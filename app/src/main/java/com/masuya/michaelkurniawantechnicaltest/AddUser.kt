package com.masuya.michaelkurniawantechnicaltest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class AddUser : AppCompatActivity(){
    private lateinit var sRole : Spinner
    private var listNmRole = ArrayList<String>()
    private var listKdRole = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val etUID = findViewById<EditText>(R.id.addId)
        val etName = findViewById<EditText>(R.id.addName)
        val etUPW = findViewById<EditText>(R.id.addPassword)
        sRole = findViewById<Spinner>(R.id.addRole)
        btnAdd.setOnClickListener({
            createUser(etUID.text.toString(), etName.text.toString(), etUPW.text.toString(), "02")
            val nextPage = Intent(this, MainActivity::class.java)
            startActivity(nextPage)
            finish()
        })
    }
    private fun fetchListRoles(){
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)

        val retrofitData = retrofit.getListRole()

        retrofitData.enqueue(object : Callback<RoleGetRequest?> {
            override fun onResponse(
                call: Call<RoleGetRequest?>,
                response: Response<RoleGetRequest?>
            ) {
                val responseBody = response.body()!!
                responseBody.result.forEach {
                    listNmRole.add(it.NmRole)
                    listKdRole.add(it.KdRole)
                }

                val spinnerAdapter = ArrayAdapter(this@AddUser, android.R.layout.simple_spinner_item, listNmRole)
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                sRole.adapter = spinnerAdapter
            }

            override fun onFailure(call: Call<RoleGetRequest?>, t: Throwable) {
                Log.d("AddUser", "onFailure: "+t.message)
            }
        })
    }
    private fun createUser(uId: String, uName: String, uPw: String, kdRole: String){
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiService::class.java)

        val retrofitData = retrofit.addUser(uId, uName, uPw, kdRole)

        retrofitData.enqueue(object : Callback<UserPostRequest?> {
            override fun onResponse(
                call: Call<UserPostRequest?>,
                response: Response<UserPostRequest?>
            ) {
                val responseBody = response.body()!!
                Log.d("AddUser", "onResponse: "+ responseBody.success)
            }

            override fun onFailure(call: Call<UserPostRequest?>, t: Throwable) {
                Log.d("AddUser", "onFailure: "+t.message)
            }
        })
    }
}