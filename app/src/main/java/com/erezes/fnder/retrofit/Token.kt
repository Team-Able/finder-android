package com.erezes.fnder.retrofit

import android.content.SharedPreferences
import android.util.Log
import com.erezes.fnder.retrofit.data.ReIssueRequest
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenManager(private val sharedPreferences: SharedPreferences) {
    fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit()
            .putString("accessToken", accessToken)
            .putString("refreshToken", refreshToken)
            .apply()
    }
}

class TokenInterceptor(private val preferences: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if(chain.request().url.encodedPath == "/auth/reissue"){
            return chain.proceed(chain.request())
        }
        val accessToken = preferences.getString("accessToken", null)
        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
        Log.d("Interceptor", "헤더에 넣음")
        println(accessToken)
        return chain.proceed(request)
    }
}

class TokenAuthenticator(
    private val sharedPreferences: SharedPreferences,
    private val apiService: ApiService
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d("authenticator", "시작")
        val refreshToken = sharedPreferences.getString("refreshToken", null) ?: return null
        Log.d("authenticator", "리프레시 토큰 $refreshToken")
        val reTokenResponse = apiService.reToken(ReIssueRequest(refreshToken)).execute()

        if(reTokenResponse.isSuccessful){
            Log.d("authenticator", "successful")
        }else{
            Log.d("authenticator", "${reTokenResponse.code()}")
        }
        val newAccessToken = reTokenResponse.body()?.data?.accessToken
        val newRefreshToken = reTokenResponse.body()?.data?.refreshToken.toString()
        Log.d("authenticator", "newAccessToken ${reTokenResponse.body()}")

        return if (newAccessToken != null) {
            TokenManager(sharedPreferences).saveTokens(newAccessToken, newRefreshToken)
            Log.d("authenticator", "진짜 마지막 됨이야 $newAccessToken")
            response.request.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
        } else {
            null
        }
    }
}