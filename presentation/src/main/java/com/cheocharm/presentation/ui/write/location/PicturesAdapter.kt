package com.cheocharm.presentation.ui.write.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cheocharm.presentation.databinding.ItemLocationPictureBinding
import com.cheocharm.presentation.model.Picture

class PicturesAdapter : ListAdapter<Picture, PicturesAdapter.ViewHolder>(PictureDiffCallback) {

    class ViewHolder(private val binding: ItemLocationPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentPicture: Picture? = null

        fun bind(picture: Picture) {
            currentPicture = picture

            with(binding.ivLocationPicture) {
                setImageURI(picture.uri)
                clipToOutline = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLocationPictureBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).run {
            holder.bind(this)
        }
    }
}

object PictureDiffCallback : DiffUtil.ItemCallback<Picture>() {
    override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
        return oldItem.uri == newItem.uri && oldItem.address == newItem.address
    }
}
