package android.test.com.pixie.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import android.test.com.pixie.helpers.RetrofitApiService
import android.test.com.pixie.models.Image
import android.test.com.pixie.utils.State
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class ImageDataSource(private val retrofitApiService: RetrofitApiService,
                      private val compositeDisposable: CompositeDisposable,
                      private val query: String)
    : PageKeyedDataSource<Int, Image>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Image>) {
        updateState(State.LOADING)
        compositeDisposable.add(
                retrofitApiService.SearchPhotos(1, query)
                        .subscribe({
                            updateState(State.DONE)
                            callback.onResult(it.imageList, null, 2)
                        }, {
                            updateState(State.ERROR)
                            setRetry(Action { loadInitial(params, callback) })
                        })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        updateState(State.LOADING)
        compositeDisposable.add(
                retrofitApiService.SearchPhotos(params.key, query)
                        .subscribe({
                            updateState(State.DONE)
                            callback.onResult(it.imageList, params.key + 1)
                        }, {
                            updateState(State.ERROR)
                            setRetry(Action { loadAfter(params, callback) })
                        })
        )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {

    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}