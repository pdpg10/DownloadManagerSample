package uz.avtobio.downloadmanagersample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        goAsync()
        if (p1 != null) {
            val text = p1.getStringExtra("test")
            Log.d("onReceive", "data $text")
        }
    }
}