package com.americanairlines.myapplication

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    private val retrofitService: RetrofitService = createService(getRetrofitInstance())

    private fun getRetrofitInstance(): Retrofit =
            Retrofit.Builder()
                    .baseUrl("https://drive.google.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    private fun createService(retrofit: Retrofit): RetrofitService =
            retrofit.create(RetrofitService::class.java)


    fun getPDF(): Call<ResponseBody> = retrofitService.getPDFFile("http://morfene.com/021.pdf")

}