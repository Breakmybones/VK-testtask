package com.example.vk.presentation.fragment.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vk.R
import com.example.vk.domain.model.FileModel

class FileAdapter(
    private val actionNext: (FileModel) -> Unit,
    private val actionSend: (FileModel) -> Unit,
    private val actionOpenFolder: (FileModel) -> Unit
): ListAdapter<FileModel, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<FileModel>() {
        override fun areItemsTheSame(
            oldItem: FileModel,
            newItem: FileModel
        ): Boolean = (oldItem as? FileModel)?.name == (newItem as? FileModel)?.name

        override fun areContentsTheSame(
            oldItem: FileModel,
            newItem: FileModel
        ): Boolean = oldItem == newItem

    }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_file -> FileHolder.create(parent, actionNext, actionSend, actionOpenFolder)
            else -> throw IllegalArgumentException("Error!")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = currentList[position]
        when (holder) {
            is FileHolder -> holder.onBind(currentItem as FileModel)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (currentList[position]) {
            is FileModel -> R.layout.item_file
            else -> throw IllegalArgumentException("Error")
        }
}