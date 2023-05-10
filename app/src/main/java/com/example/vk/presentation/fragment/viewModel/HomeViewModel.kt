package com.example.vk.presentation.fragment.viewModel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vk.domain.model.FileModel
import com.example.vk.domain.useCase.*
import java.io.File

class HomeViewModel(
    private val getDocumentsFolderUseCase: GetDocumentsFolderUseCase,
    private val getDocumentsUseCase: GetDocumentsUseCase,
    private val getDownloadsFolderUseCase: GetDownloadsFolderUseCase,
    private val getDownloadsUseCase: GetDownloadsUseCase,
    private val getImagesFolderUseCase: GetImagesFolderUseCase,
    private val getImagesUseCase: GetImagesUseCase,
    private val getMediaFolderUseCase: GetMediaFolderUseCase,
    private val getMediaUseCase: GetMediaUseCase,
    private val getVideoFolderUseCase: GetVideoFolderUseCase,
    private val getVideoUseCase: GetVideoUseCase,
    private val getFileHashUseCase: GetFileHashUseCase
): ViewModel() {

    private val _listOfFiles = MutableLiveData<MutableList<FileModel>?>(null)
    val listOfFiles: LiveData<MutableList<FileModel>?>
        get() = _listOfFiles

    private val _listOfFilesOnFolder = MutableLiveData<MutableList<FileModel>?>(null)
    val listOfFilesOnFolder: LiveData<MutableList<FileModel>?>
        get() = _listOfFilesOnFolder

    fun getAllFiles(context: Context) {
        val listOfImagesFolders = getImagesFolderUseCase(context)
        val listOfVideosFolders = getVideoFolderUseCase(context)
        val listOfMediaFolders = getMediaFolderUseCase(context)
        val listOfDownloadsFolders = getDownloadsFolderUseCase(context)
        val listOfDocumentsFolders = getDocumentsFolderUseCase(context)
        val listOfImages = getImagesUseCase("", context)
        val listOfVideos = getVideoUseCase("", context)
        val listOfMedia = getMediaUseCase("", context)
        val listOfDownloads = getDownloadsUseCase("", context)
        val listOfDocuments = getDocumentsUseCase("", context)
        _listOfFiles.value = (listOfImagesFolders +
                listOfVideosFolders +
                listOfMediaFolders +
                listOfDownloadsFolders +
                listOfDocumentsFolders +
                listOfDocuments +
                listOfDownloads +
                listOfVideos +
                listOfImages +
                listOfMedia
                ).toMutableList()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun openFolder(file: FileModel, context: Context) {
        val fileLocal = File(file.name!!)
        var filesLocal: MutableList<FileModel>? = null
        when (file.folderType) {
            "DCIM" -> filesLocal = getImagesUseCase(fileLocal.name, context)
            "DCIM_VIDEO" -> filesLocal =
                getVideoUseCase(fileLocal.name, context)
            "Document" -> filesLocal =
                getDocumentsUseCase(fileLocal.name, context)
            "Download" -> filesLocal =
                getDownloadsUseCase(fileLocal.name, context)
            "Media" -> filesLocal = getMediaUseCase(fileLocal.name, context)
        }
        _listOfFilesOnFolder.value = filesLocal
    }

    companion object {
        fun provideFactory(
            getDocumentsFolderUseCase: GetDocumentsFolderUseCase,
            getDocumentsUseCase: GetDocumentsUseCase,
            getDownloadsFolderUseCase: GetDownloadsFolderUseCase,
            getDownloadsUseCase: GetDownloadsUseCase,
            getImagesFolderUseCase: GetImagesFolderUseCase,
            getImagesUseCase: GetImagesUseCase,
            getMediaFolderUseCase: GetMediaFolderUseCase,
            getMediaUseCase: GetMediaUseCase,
            getVideoFolderUseCase: GetVideoFolderUseCase,
            getVideoUseCase: GetVideoUseCase,
            getFileHashUseCase: GetFileHashUseCase
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                HomeViewModel(
                    getDocumentsFolderUseCase,
                    getDocumentsUseCase,
                    getDownloadsFolderUseCase,
                    getDownloadsUseCase,
                    getImagesFolderUseCase,
                    getImagesUseCase,
                    getMediaFolderUseCase,
                    getMediaUseCase,
                    getVideoFolderUseCase,
                    getVideoUseCase,
                    getFileHashUseCase
                )
            }
        }
    }
}