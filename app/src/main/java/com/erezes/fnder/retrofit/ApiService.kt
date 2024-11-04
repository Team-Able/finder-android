package com.erezes.fnder.retrofit

import com.erezes.fnder.retrofit.data.ItemsListResponse
import com.erezes.fnder.retrofit.data.LogInRequest
import com.erezes.fnder.retrofit.data.LogInResponse
import com.erezes.fnder.retrofit.data.ReIssueRequest
import com.erezes.fnder.retrofit.data.ReIssueResponse
import com.erezes.fnder.retrofit.data.SignUpRequest
import com.erezes.fnder.retrofit.data.SignUpResponse
import com.erezes.fnder.retrofit.data.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/users/me")
    fun getUser(): Call<UserResponse>

    @POST("/auth/signup")
    fun signUp(@Body signUpRequest: SignUpRequest): Call<SignUpResponse>

    @POST("/auth/reissue")
    fun reToken(@Body reIssueRequest: ReIssueRequest): Call<ReIssueResponse>

    @POST("/auth/login")
    fun logIn(@Body logInRequest: LogInRequest): Call<LogInResponse>

    @GET("/items/lost")
    fun getItemsList(): Call<ItemsListResponse>
}
