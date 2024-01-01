package com.mc.rapidrescue

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TableEntryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tableData: TableData)

    @Query("SELECT * FROM final_table WHERE id = (SELECT MAX(id) FROM final_table)")
    suspend fun getLatestUserProfile(): TableData?
}