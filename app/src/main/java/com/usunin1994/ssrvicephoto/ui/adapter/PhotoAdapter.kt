package com.usunin1994.ssrvicephoto.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.usunin1994.ssrvicephoto.R
import com.usunin1994.ssrvicephoto.domain.PhotoModel

class PhotoAdapter(private val longClick: OnLongClickListener):
    RecyclerView.Adapter<PhotoViewHolder> (){

    var photos = listOf<PhotoModel>()
        set(newValue) {
            val diffCallBack = PhotoDiffCallBack(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    fun interface OnLongClickListener {
        fun onLongClick(photo: PhotoModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_card, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position], longClick)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

}