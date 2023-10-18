package com.masuya.michaelkurniawantechnicaltest

import android.telecom.Call
import javax.security.auth.callback.Callback

class MainActivity {
    val api = RetrofitClient.instance.create(ApiService::class.java)
    val call = api.getListUsers()
    call.enqueue(object : Callback<List<user>> {
        override fun onResponse(call: Call<List<user>>, response: Response<List<user>>){
            val users = resonse.body()
        }
        override fun onFailure(call: Call<List<user>>, t: Throwable){

        }
    })
}