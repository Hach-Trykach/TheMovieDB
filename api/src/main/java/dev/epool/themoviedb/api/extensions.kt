@file:Suppress("DEPRECATION")

package dev.epool.themoviedb.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.*

@UnstableDefault
fun newRetrofitInstance(apiKey: String): Retrofit {
    val build = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
//        .baseUrl("https://cors-anywhere.herokuapp.com/https://api.themoviedb.org/3/")
        .addConverterFactory(Json.nonstrict.asConverterFactory("application/json".toMediaType()))
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                )
                .addInterceptor {
                    with(it.request()) {
                        val url = url.newBuilder()
                            .setQueryParameter("api_key", apiKey)
                            .setQueryParameter("language", Locale.getDefault().toLanguageTag())
                            .build()
                        it.proceed(newBuilder().url(url).build())
                    }
                }
                .build()
        )
        .build()
    return build
}

internal inline fun <reified T> Retrofit.create(): T = create(T::class.java)
