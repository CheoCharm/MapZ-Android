package com.cheocharm.presentation.util

import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cheocharm.presentation.ui.write.WriteImageItemDecoration

object BindingAdapters {

    @BindingAdapter("app:imageLoad")
    @JvmStatic
    fun loadImage(itemView: ImageView, url: String?) {
        url ?: return
        Glide.with(itemView).load(url)
            .into(itemView)
    }

    @BindingAdapter("app:imageCircledLoad")
    @JvmStatic
    fun loadCircleImage(itemView: ImageView, url: String?) {
        url ?: return
        Glide.with(itemView).load(url).circleCrop()
            .into(itemView)
    }

    @BindingAdapter("app:tint")
    @JvmStatic
    fun ImageView.setImageTint(@ColorInt color: Int) {
        setColorFilter(color)
    }

    @BindingAdapter("app:itemMargin")
    @JvmStatic
    fun RecyclerView.setItemMargin(dp: Float) {
        addItemDecoration(WriteImageItemDecoration(dp.toInt()))
    }
}
