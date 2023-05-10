package com.example.vk.data.database

import android.content.Context
import androidx.room.Room
import com.example.vk.data.database.model.FileLocal

class DataBaseRepository(context: Context) {

    val dataBase by lazy {
        Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    private val fileDao by lazy {
        dataBase.getFileDao()
    }

    suspend fun addFile(file: FileLocal) {
        fileDao.add(file)
    }

    suspend fun getHashCode(name: String) {
        fileDao.getHashCode(name)
    }

    suspend fun setHashCode(name: String, hashCode: String) {
        fileDao.setNewHashCode(name, hashCode)
    }

    companion object {
        private const val DATABASE_NAME = "files.db"
    }

}