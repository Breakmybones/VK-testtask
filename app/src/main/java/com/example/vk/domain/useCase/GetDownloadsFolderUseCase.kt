package com.example.vk.domain.useCase

import android.content.Context
import com.example.vk.domain.model.FileModel
import com.example.vk.domain.repository.DataSourceRepository

class GetDownloadsFolderUseCase(
    private val dataSourceRepository: DataSourceRepository
) {
    operator fun invoke(context: Context): MutableList<FileModel>{
        return dataSourceRepository.getDownloadsFolders(context)
    }
}