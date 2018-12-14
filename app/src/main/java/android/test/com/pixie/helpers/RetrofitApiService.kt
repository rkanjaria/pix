package android.test.com.pixie.helpers

import android.test.com.pixie.models.Image
import android.test.com.pixie.models.ImageResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApiService {
    @GET("search/photos")
    fun searchPhotos(@Query("page") page: Int, @Query("query") query: String): Observable<ImageResponse>

    @GET("users/{user_name}/photos")
    fun getUserClickedPhotos(@Path("user_name") userName: String): Observable<List<Image>>
}