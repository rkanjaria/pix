package android.test.com.pix.helpers

import android.test.com.pix.utils.API_KEY
import android.test.com.pix.utils.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    companion object {

        fun create(): RetrofitApiService {

            val retrofit = Retrofit.Builder()
                    .client(getOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(RetrofitApiService::class.java)
        }

        private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val level = HttpLoggingInterceptor.Level.BODY
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = level
            return httpLoggingInterceptor
        }

        private fun getOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(getHttpLoggingInterceptor())
                .addNetworkInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        return chain.proceed(chain.request().newBuilder()
                                .addHeader("Authorization", API_KEY)
                                .build())
                    }
                }).build()
    }
}