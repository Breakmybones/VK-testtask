package com.example.vk.domain.model

import android.graphics.drawable.Drawable
import java.util.*

data class FileModel(
    val name: String?,
    val date: Date?,
    val size: Int?,
    val icon: Drawable?,
    val exception: String?,
    val hashCode: String?,
    val path: String?,
    val type: String?,
    val isFolder: Boolean,
    val folderType: String?
)

