package com.cheocharm.presentation.ui.write

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cheocharm.presentation.R

class WriteImageAdapter(
    private val imageUrls: List<String>,
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<WriteImageAdapter.ViewHolder>() {

    class ViewHolder(view: View, onItemClickListener: (Int) -> Unit) :
        RecyclerView.ViewHolder(view) {
        val imageView: ImageView

        init {
            imageView = view.findViewById(R.id.iv_write_image_item)

            view.setOnClickListener {
                onItemClickListener(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_write_image, parent, false)

        return ViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.imageView

        Glide.with(view.context)
            .load(imageUrls[position])
            .centerCrop()
            .placeholder(R.drawable.img_join_group)
            .into(view)
    }

    override fun getItemCount(): Int = imageUrls.size
}
