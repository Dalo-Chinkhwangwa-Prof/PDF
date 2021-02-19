package com.americanairlines.myapplication

import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.ByteBuffer

class MainViewModel : ViewModel() {

    private val retrofitInstance: RetrofitInstance = RetrofitInstance()
    val liveData: MutableLiveData<ByteArray> = MutableLiveData()

    fun makeAPi() {
        retrofitInstance.getPDF().enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("TAG_X", t.localizedMessage)
            }

            override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
            ) {
                Thread {

                    response.body()?.bytes()?.let {
                        liveData.postValue(it)
                    }

                }.start()
            }

        })
    }


}