package com.cheocharm.presentation.ui.write.diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cheocharm.presentation.databinding.ItemWriteImageBinding

class WriteImageAdapter(
    private val imageUrls: List<String>,
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<WriteImageAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemWriteImageBinding, onItemClickListener: (Int) -> Unit) :
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
        val binding = ItemWriteImageBinding.inflate(inflater, parent, false)

        return ViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageUrls[position])
    }

    override fun getItemCount(): Int = imageUrls.size
}
