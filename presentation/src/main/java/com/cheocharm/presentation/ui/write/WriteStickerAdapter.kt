package com.cheocharm.presentation.ui.write

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cheocharm.presentation.R
import com.cheocharm.presentation.model.Sticker

class WriteStickerAdapter(
    private val stickers: List<Sticker>,
    private val onItemClickListener: (Sticker) -> Unit
) : RecyclerView.Adapter<WriteStickerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView

        init {
            imageView = view.findViewById(R.id.iv_write_sticker_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_write_sticker, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.imageView
        val sticker = stickers[position]

        Glide.with(view.context).load(sticker.url).into(view)

        view.setOnClickListener {
            onItemClickListener(sticker)
        }
    }

    override fun getItemCount(): Int = 4
}