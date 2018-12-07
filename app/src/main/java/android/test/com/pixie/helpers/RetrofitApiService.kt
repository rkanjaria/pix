package android.test.com.pixie.helpers

import android.test.com.pixie.models.ImageResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {

    @GET("search/photos")
    fun SearchPhotos(@Query("page") page: Int, @Query("query") query: String): Observable<ImageResponse>
}