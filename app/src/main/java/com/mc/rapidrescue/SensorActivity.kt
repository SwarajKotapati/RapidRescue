package com.mc.rapidrescue

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rapidrescue.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.mc.rapidrescue.UserMonitoringData

class SensorActivity : AppCompatActivity() {

    private val delayMillis = 1000 // Update interval in milliseconds
    private var currentIndex = 0

    private lateinit var respiratoryRateTextView: TextView
    private lateinit var heartRateTextView: TextView
    private lateinit var speedTextView: TextView

    private var heartRateArray: Array<String> = emptyArray()
    private var repRateArray: Array<String> = emptyArray()

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sensor_activity_layout)

        EventBus.getDefault().register(this)

        val startSensorService = Intent(this, AccelerometerClass::class.java)
        startService(startSensorService)

        val startSpeedometerService = Intent(this, SpeedometerClass::class.java)
        startService(startSpeedometerService)

        respiratoryRateTextView = findViewById(R.id.respiratoryRateTextView)
        heartRateTextView = findViewById(R.id.heartRateTextView)
        speedTextView = findViewById(R.id.speedRateTextView)

        val noButton = findViewById<Button>(R.id.endrideButton)
        // Navigate To Home Button
        noButton.setOnClickListener {
            handler.removeCallbacksAndMessages(null)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val userMonitoringData = UserMonitoringData
        heartRateArray = userMonitoringData.getBadHeartRateArray()
        repRateArray = userMonitoringData.getBadRespRateArray()

        currentIndex = 0
        updateValues()

        speedTextView.text = "${0.0000}"

    }

    private fun updateValues(): Int {
        if (currentIndex < repRateArray.size && currentIndex < heartRateArray.size) {
            // Update respiratory rate
            respiratoryRateTextView.text = "${repRateArray[currentIndex]}"

            // Update heart rate
            heartRateTextView.text = "${heartRateArray[currentIndex]}"

            if(UserHealthFuzzyInferenceSystem.fuzzifyUserHealthAnamoly(
                    currentIndex,
                    repRateArray,
                    heartRateArray
                )
            ){

                currentIndex = 0

                handler.removeCallbacksAndMessages(null)
                Util.showToast(
                    this,
                    "Collision detected by Anomaly in Health Data",
                    Toast.LENGTH_SHORT
                )
                val confirmationActivity = Intent(this, ConfirmationActivity::class.java)
                startActivity(confirmationActivity)

                return 0
            }

            // Increment index for the next values
            currentIndex++

            // Schedule the next update after a delay
            handler.postDelayed({
                updateValues()
            }, delayMillis.toLong())
        }
        return 0
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onEvent(result: SpeedCallBack) {
        speedTextView.text = "%.4f".format(result.speed)

        if(CollisionDetectionFuzzyInferenceSystem.fuzzifyCollisionParameters(result.speed)){
            handler.removeCallbacksAndMessages(null)
            Util.showToast(
                this,
                "Collision detected by sudden change in speed!",
                Toast.LENGTH_SHORT
            )
            val confirmationActivity = Intent(this, ConfirmationActivity::class.java)
            startActivity(confirmationActivity)
        }
    }
}