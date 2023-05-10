package com.example.vk.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vk.data.database.model.FileLocal

@Dao
interface FileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(file: FileLocal)

    @Query("UPDATE file SET hashCode = :newHashCode WHERE name = :name")
    suspend fun setNewHashCode(name: String, newHashCode: String)

    @Query("SELECT hashCode FROM file WHERE name = :name LIMIT 1")
    suspend fun getHashCode(name: String): String

}
