package com.usunin1994.ssrvicephoto.ui.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.usunin1994.ssrvicephoto.R
import com.usunin1994.ssrvicephoto.domain.PhotoModel

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val image = itemView.findViewById<ImageView>(R.id.photoImage)

    fun bind(
        model: PhotoModel,
        longListener: PhotoAdapter.OnLongClickListener
    ) {
        itemView.setOnLongClickListener {
            longListener.onLongClick(model)
            true
        }
        image.setImageURI(model.image)
    }
}