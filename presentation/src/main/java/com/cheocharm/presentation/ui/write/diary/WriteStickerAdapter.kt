package com.cheocharm.presentation.ui.write.diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cheocharm.presentation.databinding.ItemWriteStickerBinding
import com.cheocharm.presentation.model.Sticker

class WriteStickerAdapter(
    private val stickers: List<Sticker>,
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<WriteStickerAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemWriteStickerBinding, onItemClickListener: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListener(bindingAdapterPosition)
            }
        }

        fun bind(imageUrl: String) {
            binding.imageUrl = imageUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWriteStickerBinding.inflate(inflater, parent, false)

        return ViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sticker = stickers[position]
        holder.bind(sticker.url)
    }

    override fun getItemCount(): Int = stickers.size
}
