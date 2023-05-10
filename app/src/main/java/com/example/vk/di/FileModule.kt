package com.example.vk.di

import com.example.vk.data.datasourse.DataSourceRepositoryImpl
import com.example.vk.domain.repository.DataSourceRepository
import com.example.vk.domain.useCase.*
import dagger.Module
import dagger.Provides

@Module
class FileModule {

    @Provides
    fun provideDataSourceRepository(
    ): DataSourceRepository = DataSourceRepositoryImpl()

    @Provides
    fun provideGetImagesUseCase(
        repository: DataSourceRepository
    ): GetImagesUseCase = GetImagesUseCase(repository)

    @Provides
    fun provideGetVideoUseCase(
        repository: DataSourceRepository
    ): GetVideoUseCase = GetVideoUseCase(repository)

    @Provides
    fun provideGetMediaUseCase(
        repository: DataSourceRepository
    ): GetMediaUseCase = GetMediaUseCase(repository)

    @Provides
    fun provideGetDocumentsUseCase(
        repository: DataSourceRepository
    ): GetDocumentsUseCase = GetDocumentsUseCase(repository)

    @Provides
    fun provideDownloadsImagesUseCase(
        repository: DataSourceRepository
    ): GetDownloadsUseCase = GetDownloadsUseCase(repository)


    @Provides
    fun provideGetImagesFolderUseCase(
        repository: DataSourceRepository
    ): GetImagesFolderUseCase = GetImagesFolderUseCase(repository)


    @Provides
    fun provideGetVideoFolderUseCase(
        repository: DataSourceRepository
    ): GetVideoFolderUseCase = GetVideoFolderUseCase(repository)

    @Provides
    fun provideGetMediaFolderUseCase(
        repository: DataSourceRepository
    ): GetMediaFolderUseCase = GetMediaFolderUseCase(repository)


    @Provides
    fun provideGetDownloadsFolderUseCase(
        repository: DataSourceRepository
    ): GetDownloadsFolderUseCase = GetDownloadsFolderUseCase(repository)


    @Provides
    fun provideGetDocumentsFolderUseCase(
        repository: DataSourceRepository
    ): GetDocumentsFolderUseCase = GetDocumentsFolderUseCase(repository)

    @Provides
    fun provideGetFileHashUseCase(
        repository: DataSourceRepository
    ): GetFileHashUseCase = GetFileHashUseCase(repository)
}