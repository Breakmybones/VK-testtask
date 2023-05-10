package com.example.vk.domain.useCase

import android.content.Context
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import com.example.vk.R
import com.example.vk.domain.model.FileModel
import com.example.vk.domain.repository.DataSourceRepository
import java.io.File

class GetImagesFolderUseCase(
    private val dataSourceRepository: DataSourceRepository
) {
    operator fun invoke(context: Context): MutableList<FileModel>{
        return dataSourceRepository.getImagesFolders(context)
    }
}

//override fun getImagesFolders(context: Context): MutableList<FileModel>{
//    val folders = mutableListOf<FileModel>()
//    val projection = arrayOf(
//        MediaStore.Images.Media._ID,
//        MediaStore.Images.Media.DISPLAY_NAME,
//        MediaStore.Images.Media.DATE_ADDED,
//        MediaStore.Images.Media.SIZE,
//        MediaStore.Images.Media.DATA,
//        MediaStore.Images.Media.MIME_TYPE
//    )
//
//    val selection = "${MediaStore.Images.Media.DATA} like ? "
//    val selectionArgs = arrayOf("%/DCIM/%")
//
//    val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
//
//    val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//    val contentResolver = context.contentResolver
//    val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
//
//
//
//    if (cursor != null) {
//        val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
//        val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//
//        while (cursor.moveToNext()) {
//            val name = cursor.getString(nameIndex)
//            val path = cursor.getString(dataIndex)
//            val file = File(path)
//            val parent = file.parentFile
//
//            // Если папка уже добавлена в список, то продолжаем
//            if (folders.any { it.name == parent?.name }) {
//                continue
//            }
//
//            // Добавляем папку в список и заполняем ее изображениями
//            parent?.let {
//                val folder = FileModel(it.name, null, null, ContextCompat.getDrawable(context, R.drawable.folder), null, null, null, null, true, "DCIM")
//                folders.add(folder)
//            }
//        }
//        cursor.close()
//    }
//    return folders
//
//}