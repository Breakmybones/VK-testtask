package com.example.vk.domain.useCase

import android.content.Context
import com.example.vk.domain.model.FileModel
import com.example.vk.domain.repository.DataSourceRepository

class GetImagesUseCase(
    private val dataSourceRepository: DataSourceRepository
) {
    operator fun invoke(folderName: String, context: Context): MutableList<FileModel>{
        return dataSourceRepository.getImages(folderName, context)
    }
}