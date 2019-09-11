package uz.avtobio.downloadmanagersample

import android.util.Log
import okhttp3.*
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class DownloadManager {
    private val httpClient: OkHttpClient

    init {
        httpClient = OkHttpClient.Builder()
            .writeTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    fun downloadFile(url: String, file: File) {
        val req = Request.Builder().url(url).build()
        httpClient.newCall(req).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val bytes = response.body?.byteStream()?.readBytes()
                    if (bytes != null) {
                        file.writeBytes(bytes)
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("onFailure", e.message)

            }

        })
    }
}