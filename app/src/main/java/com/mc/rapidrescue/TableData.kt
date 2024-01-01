package com.mc.rapidrescue

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "final_table")

data class TableData(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val bloodGroup: String,
    val healthDetails: String

)
