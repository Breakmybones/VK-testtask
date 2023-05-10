package com.example.vk.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vk.data.database.dao.FileDao
import com.example.vk.data.database.model.FileLocal


@Database(entities = [
    FileLocal::class
], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getFileDao(): FileDao

}