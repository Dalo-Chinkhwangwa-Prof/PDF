package com.americanairlines.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.net.URI
import java.nio.file.Files
import java.nio.file.OpenOption

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    lateinit var imageFile: File

    private lateinit var pdfView: PDFView
    private lateinit var shareButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shareButton = findViewById(R.id.share_fab)
        pdfView = findViewById(R.id.pdf_view)

        shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val path = FileProvider.getUriForFile(this, "com.americanairlines.myapplication.provider", imageFile)
                intent.putExtra(Intent.EXTRA_STREAM, path)
                Log.d("TAG_X", "" + path.path)
            } else {
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFile))
            }

            intent.type = "application/octet-stream"
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)

        }

        viewModel.makeAPi()
        viewModel.liveData.observe(this, Observer {
            displayPDF(it)
        })
    }

    private fun displayPDF(bytes: ByteArray) {
        pdfView.fromBytes(bytes).load()
        shareButton.visibility = View.VISIBLE
        imageFile = File.createTempFile("document",
                ".pdf",
                filesDir
        )
        imageFile.deleteOnExit()
        val oStream = imageFile.outputStream()
        oStream.write(bytes)
        oStream.close()
    }
}