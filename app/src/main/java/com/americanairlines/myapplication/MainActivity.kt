package com.americanairlines.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.github.barteksc.pdfviewer.PDFView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var pdfView: PDFView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("TAG_X", "TAG ${Thread.currentThread().name} !!")

        pdfView = findViewById(R.id.pdf_view)

        viewModel.makeAPi()
        viewModel.liveData.observe(this, Observer {


            displayPDF(it)

        })
    }

    private fun displayPDF(bytes: ByteArray) {
        Log.d("TAG_X", "display...")
        pdfView.fromBytes(bytes).load()
    }
}