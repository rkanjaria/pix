package android.test.com.pix.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.test.com.pix.helpers.RetrofitApiService
import android.test.com.pix.models.Image
import io.reactivex.disposables.CompositeDisposable

class ImageDataSourceFactory(
        private val retrofitApiservice: RetrofitApiService,
        private val compositeDisposable: CompositeDisposable,
        private val query: String
) : DataSource.Factory<Int, Image>() {

    val imageDatasourceLiveData = MutableLiveData<ImageDataSource>()

    override fun create(): DataSource<Int, Image> {
        val imageDataSource = ImageDataSource(retrofitApiservice, compositeDisposable, query)
        imageDatasourceLiveData.postValue(imageDataSource)
        return imageDataSource
    }
}