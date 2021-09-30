package com.example.data.network

import com.example.data.response.LoginResponse
import com.example.data.response.ProductsList
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

interface
HalanService {

//    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("/auth")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Response<LoginResponse>


    //"Bearer "

    @GET("/products")
    suspend fun getProduct(
        @Header("Authorization") token: String
    ): Response<ProductsList>


}
