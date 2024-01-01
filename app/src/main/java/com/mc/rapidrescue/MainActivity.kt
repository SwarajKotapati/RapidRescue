package com.mc.rapidrescue

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.rapidrescue.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeApplication()

        val profileDataButton = findViewById<Button>(R.id.profileData)
        val startDrivingButton = findViewById<Button>(R.id.startDriving)

        Util.updateLocation(this, this)

        // Send Alert Button
        val sendTelegramMessageButton: Button = findViewById(R.id.sendSmsButton)
        sendTelegramMessageButton.setOnClickListener {
            Util.sendMessageUpdated("")
        }

        // Navigate To Profile Data on Click
        profileDataButton.setOnClickListener {
            val intent = Intent(this, UserProfileClass::class.java)
            startActivity(intent)
        }

        // Navigate To Start Driving Screen
        startDrivingButton.setOnClickListener {
            val startSensorActivity = Intent(this, SensorActivity::class.java)
            startActivity(startSensorActivity)
        }
    }

    private fun initializeApplication() {
        // Initialize the database when the app starts
        DatabaseUtil.initializeDatabase(this)
    }


}
