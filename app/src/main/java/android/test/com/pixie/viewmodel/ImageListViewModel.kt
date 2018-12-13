package android.test.com.pixie.viewmodel

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.test.com.pixie.datasource.ImageDataSource
import android.test.com.pixie.datasource.ImageDataSourceFactory
import android.test.com.pixie.helpers.RetrofitHelper
import android.test.com.pixie.models.Image
import android.test.com.pixie.utils.QUERY
import android.test.com.pixie.utils.State
import io.reactivex.disposables.CompositeDisposable

class ImageListViewModel : ViewModel() {

    private val apiService = RetrofitHelper.create()
    var imageList: LiveData<PagedList<Image>>
    private val compositeDisposable = CompositeDisposable()
    private var query = QUERY
    private var imageDataSourceFactory: ImageDataSourceFactory
    private val pageSize = 10

    init {
        imageDataSourceFactory = ImageDataSourceFactory(apiService, compositeDisposable, query)
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()
        imageList = LivePagedListBuilder<Int, Image>(imageDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap<ImageDataSource, State>(imageDataSourceFactory.imageDataSourceLiveData, ImageDataSource::state)

    fun retry() {
        imageDataSourceFactory.imageDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty() = imageList.value?.isEmpty() ?: true

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun removeObserver(lifeCyclerOwener: LifecycleOwner, query: String) {
        imageList.removeObservers(lifeCyclerOwener)
        imageDataSourceFactory.search(query)
        imageList.value?.dataSource?.invalidate()
    }
}