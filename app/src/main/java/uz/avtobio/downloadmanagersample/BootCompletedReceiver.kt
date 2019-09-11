package uz.avtobio.downloadmanagersample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1 != null && p1.action == Intent.ACTION_BOOT_COMPLETED) {
//            TODO()
        }
    }
}