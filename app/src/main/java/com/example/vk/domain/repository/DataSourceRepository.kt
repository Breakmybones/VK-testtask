package com.example.vk.domain.repository

import android.content.Context
import com.example.vk.domain.model.FileModel
import java.io.File

interface DataSourceRepository {

    fun getImages(folderName: String, context: Context): MutableList<FileModel>

    fun getVideo(folderName: String, context: Context): MutableList<FileModel>

    fun getDownloads(folderName: String, context: Context): MutableList<FileModel>

    fun getDocuments(folderName: String, context: Context): MutableList<FileModel>

    fun getMedia(folderName: String, context: Context): MutableList<FileModel>

    fun getImagesFolders(context: Context): MutableList<FileModel>

    fun getDocumentsFolders(context: Context): MutableList<FileModel>

    fun getDownloadsFolders(context: Context): MutableList<FileModel>

    fun getVideoFolders(context: Context): MutableList<FileModel>

    fun getMediaFolders(context: Context): MutableList<FileModel>

    fun getFileHash(file: File): String?

}