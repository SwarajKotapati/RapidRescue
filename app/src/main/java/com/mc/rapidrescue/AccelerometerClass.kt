package com.mc.rapidrescue

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.widget.Toast
import kotlin.math.abs

class AccelerometerClass : Service(), SensorEventListener {

    private lateinit var accelManager: SensorManager
    private var senseAccel: Sensor? = null

    private val accelValuesX = FloatArray(550)
    private val accelValuesY = FloatArray(550)
    private val accelValuesZ = FloatArray(550)

    private var index = 0
    private var isListening = true

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {

        val mySensor = sensorEvent.sensor

        if (mySensor.type == Sensor.TYPE_ACCELEROMETER) {
            index++
            accelValuesX[index] = sensorEvent.values[0]
            accelValuesY[index] = sensorEvent.values[1]
            accelValuesZ[index] = sensorEvent.values[2]

            if (index >= 32) {
                index = 0
                accelManager.unregisterListener(this)
                callFallDetection()
                if (isListening) {
                    accelManager.registerListener(
                        this,
                        senseAccel,
                        SensorManager.SENSOR_DELAY_NORMAL
                    )
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle changes in sensor accuracy here, if needed
    }

    override fun onCreate() {

        super.onCreate()

        accelManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        senseAccel = accelManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        accelManager.registerListener(this, senseAccel, SensorManager.SENSOR_DELAY_NORMAL)

        Toast.makeText(this, "Ride Safe ! ", Toast.LENGTH_SHORT).show()
    }

    private fun callFallDetection(): Int {

        val prev = 10
        var currX: Int
        var currY: Int
        var currZ: Int

        for (i in 1..31) {

            currX = accelValuesX[i].toInt()
            currY = accelValuesY[i].toInt()
            currZ = accelValuesZ[i].toInt()

            if (abs(prev - abs(currX)) > 10 || abs(prev - abs(currY)) > 10 || abs(prev - abs(currZ)) > 10) {
                // Util.showToast(this, "Collision detected by Phone Crash", Toast.LENGTH_SHORT)
                accelManager.unregisterListener(this)
                isListening = false
                break
            }
        }
        if (!isListening) {
            val intent = Intent(this, ConfirmationActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            stopSelf()
        }
        return 0
    }

}