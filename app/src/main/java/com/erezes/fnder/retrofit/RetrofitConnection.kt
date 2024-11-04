package com.erezes.fnder.retrofit

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitConnection {
    companion object {
        private const val BASE_URL = "https://api.finder.mcv.kr"
        private var retrofit: Retrofit? = null

        fun getInstance(context: Context): Retrofit {
            if (retrofit == null) {
                val sharedPreferences = context.getSharedPreferences("tokens", Context.MODE_PRIVATE)

                val client = OkHttpClient.Builder()
                    .addInterceptor(TokenInterceptor(sharedPreferences))
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

                val apiService = retrofit!!.create(ApiService::class.java)

                retrofit = retrofit!!.newBuilder()
                    .client(client.newBuilder().authenticator(TokenAuthenticator(sharedPreferences, apiService)).build())
                    .build()
            }
            return retrofit!!
        }
    }

    class NullOnEmptyConverterFactory : Converter.Factory() {
        override fun responseBodyConverter(
            type: java.lang.reflect.Type, annotations: Array<Annotation>, retrofit: Retrofit
        ): Converter<ResponseBody, Any?> {
            val delegate = retrofit.nextResponseBodyConverter<Any?>(this, type, annotations)
            return Converter { body: ResponseBody? ->
                if (body == null || body.contentLength() == 0L) {
                    null
                } else {
                    try {
                        delegate.convert(body)
                    } catch (e: IOException) {
                        throw RuntimeException("Failed to parse response", e)
                    }
                }
            }
        }
    }
}
