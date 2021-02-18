package com.americanairlines.myapplication

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface RetrofitService {

    @Streaming
    @GET
    fun getPDFFile(@Url pdfURL: String): Call<ResponseBody>
}