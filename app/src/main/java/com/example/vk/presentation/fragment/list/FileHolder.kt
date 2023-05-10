package com.example.vk.presentation.fragment.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vk.databinding.ItemFileBinding
import com.example.vk.domain.model.FileModel

class FileHolder(
    private val binding: ItemFileBinding,
    private val action: (FileModel) -> Unit,
    private val actionSend: (FileModel) -> Unit,
    private val actionOpenFolder: (FileModel) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    private var fileModel: FileModel? = null

    init {
        itemView.setOnClickListener {
            fileModel?.let {
                if (it.isFolder) {
                    fileModel?.also(actionOpenFolder)
                }
                else {
                    fileModel?.also(action)
                }
            }
        }
        itemView.setOnLongClickListener {
            fileModel?.also(actionSend)
            true
        }
    }

    fun onBind(file: FileModel) {
        this.fileModel = file
        with(binding) {
            tvName.text = file.name
            if (file.date != null && file.size != null) {
                tvDate.text = file.date.toString()
                tvSize.text = file.size.toString() + "МБ"
            }
            ivIcon.setImageDrawable(file.icon)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            action: (FileModel) -> Unit,
            actionSend: (FileModel) -> Unit,
            actionOpenFolder: (FileModel) -> Unit,
        ): FileHolder = FileHolder(
            binding = ItemFileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            action = action,
            actionSend = actionSend,
            actionOpenFolder = actionOpenFolder
        )
    }
}