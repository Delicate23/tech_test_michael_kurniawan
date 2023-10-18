package com.masuya.michaelkurniawantechnicaltest

import android.telecom.Call

interface ApiService {
    @GET("getlistuser.php")
    fun getListUsers(): Call<List<User>>
}