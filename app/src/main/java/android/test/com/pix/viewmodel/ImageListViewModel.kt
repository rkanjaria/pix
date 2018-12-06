package android.test.com.pix.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.test.com.pix.datasource.ImageDataSource
import android.test.com.pix.datasource.ImageDataSourceFactory
import android.test.com.pix.helpers.RetrofitHelper
import android.test.com.pix.models.Image
import android.test.com.pix.utils.QUERY
import android.test.com.pix.utils.State
import io.reactivex.disposables.CompositeDisposable

class ImageListViewModel : ViewModel() {

    private val apiService = RetrofitHelper.create()
    var imageList: LiveData<PagedList<Image>>
    private val compositeDisposable = CompositeDisposable()
    private var query = QUERY
    private var imageDataSourceFactory: ImageDataSourceFactory
    private val pageSize = 5

    init {
        imageDataSourceFactory = ImageDataSourceFactory(apiService, compositeDisposable, query)
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()
        imageList = LivePagedListBuilder<Int, Image>(imageDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap<ImageDataSource, State>(imageDataSourceFactory.imageDatasourceLiveData, ImageDataSource::state)

    fun retry() {
        imageDataSourceFactory.imageDatasourceLiveData.value?.retry()
    }

    fun listIsEmpty() = imageList.value?.isEmpty() ?: true

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun setQuery(query: String?) {
        if (query != null && query.isNotEmpty())
            this.query = query
    }
}