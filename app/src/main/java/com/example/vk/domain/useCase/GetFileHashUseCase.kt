package com.example.vk.domain.useCase

import com.example.vk.domain.repository.DataSourceRepository
import java.io.File

class GetFileHashUseCase(
    private val dataSourceRepository: DataSourceRepository
) {

    operator fun invoke(file: File): String? {
        return dataSourceRepository.getFileHash(file)
    }
}