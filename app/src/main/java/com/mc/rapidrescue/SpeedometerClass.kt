package com.mc.rapidrescue

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Binder
import android.os.IBinder
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.greenrobot.eventbus.EventBus

class SpeedometerClass : Service() {

    private val binder = LocalBinder()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lastLocation: Location? = null
    private var lastTimestamp: Long = 0

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation.let { location ->
                lastTimestamp = if (lastLocation != null) {
                    val deltaTime =
                        (location.time - lastTimestamp) / 1000.0 // Convert milliseconds to seconds
                    val speed = calculateSpeed(location, lastLocation!!, deltaTime)

                    EventBus.getDefault().post(SpeedCallBack(speed))

                    location.time
                } else {
                    location.time
                }
                lastLocation = location
            }
        }
    }

    inner class LocalBinder : Binder() {
        fun getService(): SpeedometerClass = this@SpeedometerClass
    }

    override fun onCreate() {
        super.onCreate()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        requestLocationUpdates()
    }

    private fun requestLocationUpdates() {

        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000 // Update interval in milliseconds (adjust as needed)
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    private fun calculateSpeed(
        currentLocation: Location,
        lastLocation: Location,
        deltaTime: Double
    ): Float {
        // Calculate speed using distance and time
        val distance = currentLocation.distanceTo(lastLocation)
        return (distance / deltaTime).toFloat()
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
