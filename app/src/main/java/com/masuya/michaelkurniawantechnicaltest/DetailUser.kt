package com.masuya.michaelkurniawantechnicaltest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailUser : AppCompatActivity() {
    lateinit var etName : TextInputEditText
    lateinit var etUPW : TextInputEditText
    lateinit var UID : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        val param = intent.getStringExtra("UID")
        UID = param.toString()
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        etName = findViewById(R.id.editNama)
        etUPW = findViewById(R.id.editPassword)
        val tvDetail = findViewById<TextView>(R.id.tvDetail)
        tvDetail.setText(UID.toString())

        btnEdit.setOnClickListener({
            editUser(UID.toString(), etName.text.toString(), etUPW.text.toString(), "01")
            val nextPage = Intent(this, MainActivity::class.java)
            startActivity(nextPage)
            finish()
        })
        btnDelete.setOnClickListener({
            showAlertDialog()
        })
        fetchUser()
    }
    private fun fetchUser(){
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)

        val retrofitData = retrofit.getListUsers()
        retrofitData.enqueue(object : Callback<UserGetRequest?> {
            override fun onResponse(call: Call<UserGetRequest?>, response: Response<UserGetRequest?>) {
                val responseBody = response.body()!!
                etName.setText(responseBody.result.first().UName)
                etUPW.setText(responseBody.result.first().UPW)

            }

            override fun onFailure(call: Call<UserGetRequest?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: "+t.message)
            }
        })
    }
    private fun editUser(uId: String, uName: String, uPw: String, kdRole: String){
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)

        val retrofitData = retrofit.updateUser(uId, uName, uPw, kdRole)

        retrofitData.enqueue(object : Callback<UserPostRequest?> {
            override fun onResponse(
                call: Call<UserPostRequest?>,
                response: Response<UserPostRequest?>
            ) {
                val responseBody = response.body()!!
                Log.d("AddUser", "onResponse: "+ responseBody.result.first().Response)
            }

            override fun onFailure(call: Call<UserPostRequest?>, t: Throwable) {
                Log.d("AddUser", "onFailure: "+t.message)
            }
        })

    }
    private fun deleteUser(uId: String){
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)

        val retrofitData = retrofit.deleteUser(uId)

        retrofitData.enqueue(object : Callback<UserPostRequest?> {
            override fun onResponse(
                call: Call<UserPostRequest?>,
                response: Response<UserPostRequest?>
            ) {
                val responseBody = response.body()!!
                Log.d("AddUser", "onResponse: "+ responseBody.result.first().Response)
            }

            override fun onFailure(call: Call<UserPostRequest?>, t: Throwable) {
                Log.d("AddUser", "onFailure: "+t.message)
            }
        })
    }
    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("DELETE USER")
        builder.setMessage("Are you sure to delete this user?i")
        builder.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()
            deleteUser(UID.toString())
            val nextPage = Intent(this, MainActivity::class.java)
            startActivity(nextPage)
            finish()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
            val nextPage = Intent(this, MainActivity::class.java)
            startActivity(nextPage)
            finish()
        }

        val dialog = builder.create()
        dialog.show()
    }
}