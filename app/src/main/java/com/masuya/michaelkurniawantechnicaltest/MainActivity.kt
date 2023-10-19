package com.masuya.michaelkurniawantechnicaltest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://masuya1.freeddns.org:91/cobaapp2/masuyates/technicaltest/"
class MainActivity : AppCompatActivity() {
    private lateinit var  newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<com.masuya.michaelkurniawantechnicaltest.User>
    lateinit var  btnToAddPage : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newRecyclerView = findViewById(R.id.rvListUser)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf<com.masuya.michaelkurniawantechnicaltest.User>()
        btnToAddPage = findViewById(R.id.btnToAddPage)
        btnToAddPage.setOnClickListener({
            val nextPage = Intent(this, AddUser::class.java)
            startActivity(nextPage)
            finish()
        })
        fetchListUsers()
    }
    private fun fetchListUsers(){
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiService::class.java)

        val retrofitData = retrofit.getListUsers()
        retrofitData.enqueue(object : Callback<UserGetRequest?> {
            override fun onResponse(call: Call<UserGetRequest?>, response: Response<UserGetRequest?>) {
                val responseBody = response.body()!!
                newRecyclerView.adapter = RvAdapter(this@MainActivity, responseBody.result)
            }

            override fun onFailure(call: Call<UserGetRequest?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: "+t.message)
            }
        })
    }
}