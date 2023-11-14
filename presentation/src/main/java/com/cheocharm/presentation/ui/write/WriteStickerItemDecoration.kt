package com.cheocharm.presentation.ui.write

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class WriteStickerItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = margin
    }
}
