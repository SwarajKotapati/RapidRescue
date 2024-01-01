package com.mc.rapidrescue

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.rapidrescue.R
import com.mc.rapidrescue.DatabaseUtil.getUserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProfileClass : AppCompatActivity() {

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var bloodGroupEditText: EditText
    private lateinit var healthDetailsEditText: EditText

    private var firstName: String? = null
    private var lastName: String? = null
    private var phoneNumber: String? = null
    private var bloodGroup: String? = null
    private var healthDetails: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        initializeApplication()

        // Initialize UI components
        firstNameEditText = findViewById(R.id.editTextFirstName)
        lastNameEditText = findViewById(R.id.editTextLastName)
        phoneNumberEditText = findViewById(R.id.editTextPhoneNumber)
        bloodGroupEditText = findViewById(R.id.editTextBloodGroup)
        healthDetailsEditText = findViewById(R.id.editTextHealthDetails)

        GlobalScope.launch(Dispatchers.Main) {
            val latestUserProfile = withContext(Dispatchers.IO) {
                // Move the blocking call to IO dispatcher
                getUserData()
            }
            if (latestUserProfile != null) {
                firstNameEditText.setText(latestUserProfile.firstName)
                lastNameEditText.setText(latestUserProfile.lastName)
                phoneNumberEditText.setText(latestUserProfile.phoneNumber)
                bloodGroupEditText.setText(latestUserProfile.bloodGroup)
                healthDetailsEditText.setText(latestUserProfile.healthDetails)
            }
        }

        val submitButton: Button = findViewById(R.id.buttonSubmit)
        val homeButton: Button = findViewById(R.id.homeButton)

        submitButton.setOnClickListener {
            // On submit button click, save the data to local variables
            saveUserData()
        }

        homeButton.setOnClickListener {
            // On submit button click, save the data to local variables
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveUserData() {
        // Get data from EditText fields
        firstName = firstNameEditText.text.toString()
        lastName = lastNameEditText.text.toString()
        phoneNumber = phoneNumberEditText.text.toString()
        bloodGroup = bloodGroupEditText.text.toString()
        healthDetails = healthDetailsEditText.text.toString()

        DatabaseUtil.saveUserData(
            this,
            firstName.toString(),
            lastName.toString(),
            phoneNumber.toString(),
            bloodGroup.toString(),
            healthDetails.toString()
        )

    }

    private fun initializeApplication() {
        // Initialize the database when the app starts
        DatabaseUtil.initializeDatabase(this)
    }

}