package com.mc.rapidrescue

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.rapidrescue.R
import com.mc.rapidrescue.Util.sendMessageUpdated

class ConfirmationActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private val delayMillis = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        val yesButton = findViewById<Button>(R.id.yesButton)
        val noButton = findViewById<Button>(R.id.noButton)

        // Navigate To Home Button
        noButton.setOnClickListener {
            handler.removeCallbacksAndMessages(null)
            val intent = Intent(this, SensorActivity::class.java)
            startActivity(intent)
        }

        // Trigger Alert
        yesButton.setOnClickListener {
            handler.removeCallbacksAndMessages(null)
            sendAlert()
        }

        handler.postDelayed({
            sendAlert()
        }, delayMillis.toLong())
    }

    private fun sendAlert() {
        Util.updateLocation(this, this)

        Util.showToast(this, "Sending Alert")
        sendMessageUpdated("")
        Util.showToast(this, "Alert Sent Successfully!")

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}