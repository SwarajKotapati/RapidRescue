package com.mc.rapidrescue

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TableData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tableEntryDao(): TableEntryDao
}