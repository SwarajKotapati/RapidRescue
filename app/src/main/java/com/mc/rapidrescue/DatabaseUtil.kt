package com.mc.rapidrescue

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DatabaseUtil {
    private lateinit var appDatabase: AppDatabase

    fun initializeDatabase(context: Context) {
        appDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "Rapid_Rescue_DB" // Replace with your database name
        ).build()
    }

    fun saveUserData(
        context: Context,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        bloodGroup: String,
        healthDetails: String
    ) {
        val tableData = TableData(
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            bloodGroup = bloodGroup,
            healthDetails = healthDetails
        )

        val tableEntryDao = appDatabase.tableEntryDao()

        GlobalScope.launch {
            tableEntryDao.insert(tableData)
        }

        Util.showToast(context, "Data Saved Successfully!")
    }

    suspend fun getUserData(): TableData? {
        return withContext(Dispatchers.IO) {
            val tableEntryDao = appDatabase.tableEntryDao()
            return@withContext tableEntryDao.getLatestUserProfile()
        }
    }

}
