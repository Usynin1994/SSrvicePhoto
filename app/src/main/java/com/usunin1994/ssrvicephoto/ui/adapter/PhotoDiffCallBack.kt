package com.usunin1994.ssrvicephoto.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.usunin1994.ssrvicephoto.domain.PhotoModel

class PhotoDiffCallBack (
    private val oldList: List<PhotoModel>,
    private val newList: List<PhotoModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPhoto = oldList[oldItemPosition]
        val newPhoto = newList[newItemPosition]
        return oldPhoto.image == newPhoto.image
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPhoto = oldList[oldItemPosition]
        val newPhoto = newList[newItemPosition]
        return oldPhoto == newPhoto
    }
}