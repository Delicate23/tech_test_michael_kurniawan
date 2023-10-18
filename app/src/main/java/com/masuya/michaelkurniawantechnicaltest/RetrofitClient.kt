package com.masuya.michaelkurniawantechnicaltest

object RetrofitClient {
    private const val BASE_URL = "http://masuya1.freeddns.org:91/cobaapp2/masuyates/technicaltest/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}