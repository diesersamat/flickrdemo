package me.sgayazov.flickrdemo.dataprovider

import me.sgayazov.flickrdemo.networking.RetrofitService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://api.flickr.com"
private const val API_KEY = "42f2bdb87a29e9850227c697b1063afa"

class NetworkDataProvider {

    private var apiService: RetrofitService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .client(getClient())
            .build()
            .create(RetrofitService::class.java)

    private fun getClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
                .addInterceptor({ chain ->
                    val original = chain.request()
                    val originalHttpUrl = original.url()

                    val url = originalHttpUrl.newBuilder()
                            .addQueryParameter("api_key", API_KEY)
                            .addQueryParameter("format", "json")
                            .addQueryParameter("nojsoncallback", "1")
                            .build()

                    val requestBuilder = original.newBuilder().url(url)
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }).build()
        return client
    }

    fun getTopTagsList(maxCategoriesCount: Int) = apiService.getTopTagsList(maxCategoriesCount)

    fun searchForPhotos(query: String) = apiService.searchForPhotos(query)
}