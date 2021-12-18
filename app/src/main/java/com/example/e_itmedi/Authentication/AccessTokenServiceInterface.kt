package com.example.e_itmedi.Authentication

import com.example.e_itmedi.Authentication.SignUp.ResponseData
import com.example.e_itmedi.Login.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccessTokenServiceInterface {
    @POST("/api/v1/register")
    @FormUrlEncoded
    fun registeruser(
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("c_password") cPassword: String?,
        @Field("name") name: String?
    ): Call<ResponseData?>?

    @POST("/api/v1/login")
    @FormUrlEncoded
    fun logijUser(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Call<LoginResponse?>?
}