package com.masuya.michaelkurniawantechnicaltest

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("insert.php")
    fun addUser(
        @Field("UID")UID: String,
        @Field("UName")UName: String,
        @Field("UPW")UPW: String,
        @Field("KdRole")KdRole: String): Call<UserPostRequest>
    @GET("getlistuser.php")
    fun getListUsers(): Call<UserGetRequest>

    @FormUrlEncoded
    @POST("selectuser.php")
    fun getUserDetail(
        @Field("UID")UID: String
    ):Call<UserPostRequest>
    @GET("getlistrole.php")
    fun getListRole(): Call<RoleGetRequest>

    @FormUrlEncoded
    @POST("edit.php")
    fun updateUser(
        @Field("UID")UID: String,
        @Field("UName")UName: String,
        @Field("UPW")UPW: String,
        @Field("KdRole")KdRole: String
    ):Call<UserPostRequest>
    @FormUrlEncoded
    @POST("delete.php")
    fun deleteUser(
        @Field("UID")UID: String
    ):Call<UserPostRequest>


}