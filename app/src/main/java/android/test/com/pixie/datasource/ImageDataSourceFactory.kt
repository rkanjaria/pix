package android.test.com.pixie.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.test.com.pixie.helpers.RetrofitApiService
import android.test.com.pixie.models.Image
import io.reactivex.disposables.CompositeDisposable

class ImageDataSourceFactory(
        private val retrofitApiservice: RetrofitApiService,
        private val compositeDisposable: CompositeDisposable,
        private var query: String
) : DataSource.Factory<Int, Image>() {

    val imageDatasourceLiveData = MutableLiveData<ImageDataSource>()

    override fun create(): DataSource<Int, Image> {
        val imageDataSource = ImageDataSource(retrofitApiservice, compositeDisposable, query)
        imageDatasourceLiveData.postValue(imageDataSource)
        return imageDataSource
    }

    fun search(query: String) {
        this.query = query
    }
}