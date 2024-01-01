package com.mc.rapidrescue

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object Util {

    var globalLongitude = "NA"
    var globalLatitude = "NA"
    private const val myPermissionsRequestLocation = 123

    private const val botToken = "6845482029:AAEP7yNJzXT8D75A-KhcL4gwK3WyFunzvXk"

    private val chatId = "1228526284"
    //private const val chatId = "348195575" // Rushabh

    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

    fun sendMessageUpdated(message: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val latestUserProfile = DatabaseUtil.getUserData()

                if (latestUserProfile != null) {
                    // Extract user details from the latestUserProfile
                    val firstName = latestUserProfile.firstName
                    val lastName = latestUserProfile.lastName
                    val phoneNumber = latestUserProfile.phoneNumber
                    val bloodGroup = latestUserProfile.bloodGroup
                    val healthDetails = latestUserProfile.healthDetails

                    // Construct the message with user details and location
                    val completeMessage = """
                    User Details:
                    First Name: $firstName
                    Last Name: $lastName
                    Phone Number: $phoneNumber
                    Blood Group: $bloodGroup
                    Health Details: $healthDetails

                    Location Details:
                    Latitude: $globalLatitude
                    Longitude: $globalLongitude

                    Additional Message: $message
                """.trimIndent()

                    // Use your existing code to send the completeMessage
                    // Copy the relevant parts from your existing sendMessage function

                    val urlString = "https://api.telegram.org/bot$botToken/sendMessage"
                    val url = URL(urlString)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "POST"
                    connection.setRequestProperty("Content-Type", "application/json")
                    connection.doOutput = true

                    val payload = """
                    {
                        "chat_id": "$chatId",
                        "text": "$completeMessage"
                    }
                """.trimIndent()

                    val outputStream = OutputStreamWriter(connection.outputStream)
                    outputStream.write(payload)
                    outputStream.flush()

                    // Log.d("MainActivity", "Line 50") // Use Log for logging

                    val responseCode = connection.responseCode

                    // Log.d("MainActivity", responseCode.toString())

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Message sent successfully
                        Log.d("MainActivity", "Message Sent Successfully")
                    } else {
                        Log.e("MainActivity", "Error Faced") // Use Log.e for error messages
                        // Handle error (e.g., log or show a message)
                    }

                    connection.disconnect()
                } else {
                    Log.e("MainActivity", "Latest user profile is null")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Exception: ${e.message}", e)
            }
        }
    }

    private fun checkPermission(context: Context, activity: Activity) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                myPermissionsRequestLocation
            )
        }
    }

    fun updateLocation(context: Context, activity: Activity) {
        // Location Services Code
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        checkPermission(context, activity)

        Log.d("MainActivity", "After Permission Code")
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    globalLatitude = latitude.toString()
                    globalLongitude = longitude.toString()

                    Log.d("MainActivity", "Location Code")
                }
            }
            .addOnFailureListener { e ->
                // Handle failure
                Log.d("MainActivity", "Location Error Code")
            }.addOnCompleteListener { e ->
                // showToast(context, "Sending Alert")

            }
    }
}