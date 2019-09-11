package uz.avtobio.downloadmanagersample

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.BatteryManager
import android.os.Bundle
import android.os.Environment
import android.telephony.SmsMessage
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

private const val ACTION = "uz.avtobio.downloadmanagersample.SOME_ACTION"

class MainActivity : AppCompatActivity() {
    private var reveiver = DownloadCompleteReceiver()
    private val batteryReceiver = BatteryReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { startDownload() }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        registerReceiver(reveiver, filter)

        val batteryFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        registerReceiver(batteryReceiver, batteryFilter)
        val fil1 = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        registerReceiver(SmsReceiver(), fil1)
    }

    //https://www.skypoynt.com/hubfs/back-view-of-focused-programmer-writing-code-and-PDVAFDS.jpg
//    https://indigo.co.ua/wp-content/uploads/2017/10/bg-java.jpg
    override fun onStop() {
        super.onStop()

        unregisterReceiver(reveiver)
        unregisterReceiver(batteryReceiver)
//        SmsManager.getDefault().sendTextMessage("",null,"sms",null)
//        LocalBroadcastManager.getInstance(this).
    }

    private fun startDownload() {
//        val intent = Intent(ACTION)
//        intent.putExtra("test", "some value")
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        val url = et_url.text.toString()
        val matcher = Patterns.WEB_URL.matcher(url)
        if (matcher.matches()) {
            //IPC
            val downloadManager =
                getSystemService(Context.DOWNLOAD_SERVICE) as android.app.DownloadManager
            val request = android.app.DownloadManager.Request(Uri.parse(url))
            request.setTitle("download some file")
            request.setDescription("download file desc")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            request.setAllowedOverMetered(true)
            request.setAllowedOverRoaming(true)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "test11")
            downloadManager.enqueue(request)
//            request.setRequiresCharging(true)


        } else {
            Toast.makeText(this, "url not corrected", Toast.LENGTH_SHORT).show()
        }
    }

    class DownloadCompleteReceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Toast.makeText(p0, "Download Completed", Toast.LENGTH_SHORT).show()
        }
    }

    inner class BatteryReceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val level = p1!!.getIntExtra(BatteryManager.EXTRA_LEVEL, 1) / intent.getIntExtra(
                BatteryManager.EXTRA_SCALE,
                1
            )
            tv_battery.text = "$level %"
        }
    }

    inner class SmsReceiver : BroadcastReceiver() {

        override fun onReceive(p0: Context, p1: Intent) {
            val rawSms = p1.extras!!.get("pdus") as Array<*>
            for (smsIt in rawSms) {
                val sms: SmsMessage = SmsMessage.createFromPdu(smsIt as ByteArray?)
                Toast.makeText(
                    p0,
                    sms.originatingAddress + " " + sms.displayMessageBody,
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
}
