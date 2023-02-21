package com.example.cashify

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.core.graphics.toColor
import com.example.cashify.databinding.ActivityMainBinding
import kotlin.system.measureTimeMillis

class SplashActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        createNotificationChannel()



//        val intent = Intent(this, Notification::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
//        val alarmManager: AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
//        val calendar = Calendar.getInstance()
//
//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis + 20000, pendingIntent )

//        val calendar = Calendar.getInstance()
//        calendar.set(Calendar.HOUR_OF_DAY,18)
//        calendar.set(Calendar.MINUTE,0)
//        calendar.set(Calendar.SECOND,0)
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+2)
//
//
//        val alarmManager: AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
//        val intent = Intent(this, Notification::class.java)
//        val pi = PendingIntent.getService(this, 0, intent, 0)
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_DAY, pi)



        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.rgb(107,67,190));

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().setNavigationBarDividerColor(Color.rgb(107,67,190));
        }
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }

    fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name: CharSequence = "Npasticion"
            val description: String = "pasicion channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel("channel1",name, importance)
            channel.description = description

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}