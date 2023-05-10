package com.example.vk.data.datasourse

import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.vk.R
import com.example.vk.domain.model.FileModel
import com.example.vk.domain.repository.DataSourceRepository
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import java.util.*
import kotlin.experimental.and

class DataSourceRepositoryImpl(): DataSourceRepository {

    override fun getImages(folderName: String, context: Context): MutableList<FileModel> {
        val images = mutableListOf<FileModel>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.MIME_TYPE
        )

        val selection = "${MediaStore.Images.Media.DATA} like ? "
        val selectionArgs = arrayOf("%/DCIM/$folderName/%")

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

        if (cursor != null) {
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dateIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
            val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            val mimeTypeIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idIndex)
                val name = cursor.getString(nameIndex)
                val date = Date(cursor.getLong(dateIndex) * 1000)
                val size = cursor.getInt(sizeIndex) / 1024 // в килобайтах
                val path = cursor.getString(dataIndex)
                val type = cursor.getString(mimeTypeIndex)
                val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(type)

                var item: FileModel?
                when (extension) {
                    "jpg" -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.jpg), extension, getFileHash(File(path)), path, type, false, null)
                    "png" -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.png), extension, getFileHash(File(path)), path, type, false, null)
                    else -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.documents), extension, getFileHash(File(path)), path, type, false, null)
                }
                images.add(item)
            }
            cursor.close()
        }
        return images
    }


    override fun getVideo(folderName: String, context: Context): MutableList<FileModel> {
        val videos = mutableListOf<FileModel>()
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.MIME_TYPE
        )

        val selection = "${MediaStore.Video.Media.DATA} like ? "
        val selectionArgs = arrayOf("%/DCIM/$folderName/%")

        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

        if (cursor != null) {
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val dateIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)
            val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val mimeTypeIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idIndex)
                val name = cursor.getString(nameIndex)
                val date = Date(cursor.getLong(dateIndex) * 1000)
                val size = cursor.getInt(sizeIndex) / 1024 // в килобайтах
                val path = cursor.getString(dataIndex)
                val type = cursor.getString(mimeTypeIndex)
                val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(type)

                var item: FileModel?
                when (extension) {
                    "mp4" -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.mp4), extension, getFileHash(File(path)), path, type, false, null)
                    "mkv" -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.mkv), extension, getFileHash(File(path)), path, type, false, null)
                    else -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.documents), extension, getFileHash(File(path)), path, type, false, null)
                }
                videos.add(item)
            }
            cursor.close()
        }
        return videos
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun getDownloads(folderName: String, context: Context): MutableList<FileModel> {
        val downloads = mutableListOf<FileModel>()
        val projection = arrayOf(
            MediaStore.Downloads._ID,
            MediaStore.Downloads.DISPLAY_NAME,
            MediaStore.Downloads.DATE_ADDED,
            MediaStore.Downloads.SIZE,
            MediaStore.Downloads.DATA,
            MediaStore.Downloads.MIME_TYPE
        )

        val selection = "${MediaStore.Downloads.DATA} like ? "
        val selectionArgs = arrayOf("%/Download/$folderName/%")

        val sortOrder = "${MediaStore.Downloads.DATE_ADDED} DESC"

        val uri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

        if (cursor != null) {
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Downloads._ID)
            val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Downloads.DISPLAY_NAME)
            val dateIndex = cursor.getColumnIndexOrThrow(MediaStore.Downloads.DATE_ADDED)
            val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Downloads.SIZE)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Downloads.DATA)
            val mimeTypeIndex = cursor.getColumnIndexOrThrow(MediaStore.Downloads.MIME_TYPE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idIndex)
                val name = cursor.getString(nameIndex)
                val date = Date(cursor.getLong(dateIndex) * 1000)
                val size = cursor.getInt(sizeIndex) / 1024 // в килобайтах
                val path = cursor.getString(dataIndex)
                val type = cursor.getString(mimeTypeIndex)
                val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(type)

                var item: FileModel?
                when (extension) {
                    "jpg" -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.jpg), extension, getFileHash(File(path)), path, type, false, null)
                    "png" -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.png), extension, getFileHash(File(path)), path, type, false, null)
                    else -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.documents), extension, getFileHash(File(path)), path, type, false, null)
                }
                downloads.add(item)
            }
            cursor.close()
        }
        return downloads
    }



    override fun getDocuments(folderName: String, context: Context): MutableList<FileModel> {
        val documents = mutableListOf<FileModel>()
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.MIME_TYPE
        )
        val selection = "${MediaStore.Files.FileColumns.DATA} LIKE ?"
        val selectionArgs = arrayOf("%/Documents/$folderName/%")
        val sortOrder = "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"
        val uri = MediaStore.Files.getContentUri("external")
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
        if (cursor != null) {
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val dateIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED)
            val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            val mimeTypeIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idIndex)
                val name = cursor.getString(nameIndex)
                val date = Date(cursor.getLong(dateIndex) * 1000)
                val size = cursor.getInt(sizeIndex) / 1024 // в килобайтах
                val path = cursor.getString(dataIndex)
                val type = cursor.getString(mimeTypeIndex)
                val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(type)
                var item: FileModel?
                when (extension) {
                    "pdf" -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.pdf), extension, getFileHash(File(path)), path, type, false, null)
                    "ppt", "pptx" -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.ppt), extension, getFileHash(File(path)), path, type, false, null)
                    "xls", "xlsx" -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.xls), extension, getFileHash(File(path)), path, type, false, null)
                    "txt" -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.txt), extension, getFileHash(File(path)), path, type, false, null)
                    else -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.documents), extension, getFileHash(File(path)), path, type, false, null)
                }
                documents.add(item)
            }
            cursor.close()
        }
        return documents
    }


    override fun getMedia(folderName: String, context: Context): MutableList<FileModel> {
        val audioList = mutableListOf<FileModel>()

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.MIME_TYPE
        )

        val selection = "${MediaStore.Audio.Media.DATA} like ? "
        val selectionArgs = arrayOf("%/Music/$folderName/%")

        val sortOrder = "${MediaStore.Audio.Media.DATE_ADDED} DESC"

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

        if (cursor != null) {
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val dateIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)
            val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val mimeTypeIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idIndex)
                val name = cursor.getString(nameIndex)
                val date = Date(cursor.getLong(dateIndex) * 1000)
                val size = cursor.getInt(sizeIndex) / 1024 // в килобайтах
                val path = cursor.getString(dataIndex)
                val type = cursor.getString(mimeTypeIndex)
                val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(type)

                var item: FileModel?
                when (extension) {
                    "mp3" -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.mp3), extension, getFileHash(File(path)), path, type, false, null)
                    else -> item = FileModel(name, date, size, ContextCompat.getDrawable(context, R.drawable.documents), extension, getFileHash(File(path)), path, type, false, null)
                }

                audioList.add(item)
            }
            cursor.close()
        }

        return audioList
    }


    override fun getImagesFolders(context: Context): MutableList<FileModel>{
        val folders = mutableListOf<FileModel>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.MIME_TYPE
        )

        val selection = "${MediaStore.Images.Media.DATA} like ? "
        val selectionArgs = arrayOf("%/DCIM/%")

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)



        if (cursor != null) {
            val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameIndex)
                val path = cursor.getString(dataIndex)
                val file = File(path)
                val parent = file.parentFile

                // Если папка уже добавлена в список, то продолжаем
                if (folders.any { it.name == parent?.name }) {
                    continue
                }

                // Добавляем папку в список и заполняем ее изображениями
                parent?.let {
                    val folder = FileModel(it.name, null, null, ContextCompat.getDrawable(context, R.drawable.folder), null, null, null, null, true, "DCIM")
                    folders.add(folder)
                }
            }
            cursor.close()
        }
        return folders

    }

    override fun getDocumentsFolders(context: Context): MutableList<FileModel>{
        val folders = mutableListOf<FileModel>()
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.MIME_TYPE
        )

        val selection = "${MediaStore.Files.FileColumns.DATA} like ? "
        val selectionArgs = arrayOf("%/Document/%")

        val sortOrder = "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"

        val uri = MediaStore.Files.getContentUri("external")
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

        if (cursor != null) {
            val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameIndex)
                val path = cursor.getString(dataIndex)
                val file = File(path)
                val parent = file.parentFile

                // If the folder is already added to the list, then continue
                if (folders.any { it.name == parent?.name }) {
                    continue
                }

                // Add the folder to the list and populate it with files
                parent?.let {
                    val folder = FileModel(it.name, null, null, ContextCompat.getDrawable(context, R.drawable.folder), null, null, null, null, true, "Document")
                    folders.add(folder)
                }
            }
            cursor.close()
        }
        return folders
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun getDownloadsFolders(context: Context): MutableList<FileModel> {
        val folders = mutableListOf<FileModel>()
        val projection = arrayOf(
            MediaStore.Downloads._ID,
            MediaStore.Downloads.DISPLAY_NAME,
            MediaStore.Downloads.DATE_ADDED,
            MediaStore.Downloads.SIZE,
            MediaStore.Downloads.DATA
        )

        val selection = "${MediaStore.Downloads.DATA} like ?"
        val selectionArgs = arrayOf("%/Download%")

        val sortOrder = "${MediaStore.Downloads.DATE_ADDED} DESC"

        val uri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

        if (cursor != null) {
            val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Downloads.DISPLAY_NAME)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Downloads.DATA)

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameIndex)
                val path = cursor.getString(dataIndex)
                val file = File(path)
                val parent = file.parentFile

                // Если папка уже добавлена в список, то продолжаем
                if (folders.any { it.name == parent?.name }) {
                    continue
                }

                // Добавляем папку в список и заполняем ее файлами
                parent?.let {
                    val folder = FileModel(it.name, null, null, ContextCompat.getDrawable(context, R.drawable.folder), null, null, null, null, true, "Download")
                    folders.add(folder)
                }
            }
            cursor.close()
        }
        return folders
    }




    override fun getVideoFolders(context: Context): MutableList<FileModel>{
        val folders = mutableListOf<FileModel>()
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.MIME_TYPE
        )

        val selection = "${MediaStore.Video.Media.DATA} like ? "
        val selectionArgs = arrayOf("%/DCIM/%")

        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

        if (cursor != null) {
            val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameIndex)
                val path = cursor.getString(dataIndex)
                val file = File(path)
                val parent = file.parentFile

                // Если папка уже добавлена в список, то продолжаем
                if (folders.any { it.name == parent?.name }) {
                    continue
                }

                // Добавляем папку в список и заполняем ее видео
                parent?.let {
                    val folder = FileModel(it.name, null, null, ContextCompat.getDrawable(context, R.drawable.folder), null, null, null, null, true, "DCIM_VIDEO")
                    folders.add(folder)
                }
            }
            cursor.close()
        }
        return folders
    }



    override fun getMediaFolders(context: Context): MutableList<FileModel>{
        val folders = mutableListOf<FileModel>()
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.MIME_TYPE
        )

        val selection = "${MediaStore.Audio.Media.DATA} like ? "
        val selectionArgs = arrayOf("%/Media/%")

        val sortOrder = "${MediaStore.Audio.Media.DATE_ADDED} DESC"

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

        if (cursor != null) {
            val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameIndex)
                val path = cursor.getString(dataIndex)
                val file = File(path)
                val parent = file.parentFile

                // Если папка уже добавлена в список, то продолжаем
                if (folders.any { it.name == parent?.name }) {
                    continue
                }

                // Добавляем папку в список и заполняем ее изображениями
                parent?.let {
                    val folder = FileModel(it.name, null, null, ContextCompat.getDrawable(context, R.drawable.folder), null, null, null, null, true, "Media")
                    folders.add(folder)
                }
            }
            cursor.close()
        }
        return folders
    }

    override fun getFileHash(file: File): String? {
        try {
            val digest = MessageDigest.getInstance("SHA-256")
            val fis = FileInputStream(file)
            val byteArray = ByteArray(1024)
            var bytesCount = fis.read(byteArray)
            while (bytesCount != -1) {
                digest.update(byteArray, 0, bytesCount)
                bytesCount = fis.read(byteArray)
            }
            fis.close()

            val bytes = digest.digest()

            val sb = StringBuilder()
            for (i in bytes.indices) {
                sb.append(Integer.toString((bytes[i] and 0xff.toByte()) + 0x100, 16).substring(1))
            }
            return sb.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
