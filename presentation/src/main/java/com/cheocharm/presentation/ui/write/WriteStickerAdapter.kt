package com.cheocharm.presentation.ui.write

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cheocharm.presentation.R

class WriteStickerAdapter(private val onItemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<WriteStickerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView

        init {
            imageView = view.findViewById(R.id.iv_write_image_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_write_image, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.imageView

        Glide.with(view.context).load(stickerUrls[position]).into(view)

        view.setOnClickListener {
            onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int = 4

    companion object {
        // TODO: 서버에 스티커 파일 업로드 후 URL 변경
        val stickerUrls = listOf(
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Emoji/IMG_6148.JPG",
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary//5eda379c-a8be-4c41-bfbf-a1e984c457ff",
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/1/afd156ea-6257-4c30-9d06-f8b1ebf37609",
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/1/f4424c08-dfd3-461b-b9e3-3cf09f984f8a",
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/fe1189fb-c83d-4792-9c45-6e3bfbd77cda",
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/f3284be0-c6d9-43be-8399-e6ebd30603ae"
        )
    }
}
