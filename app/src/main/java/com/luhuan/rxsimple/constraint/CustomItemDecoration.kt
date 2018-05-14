package com.luhuan.rxsimple.constraint

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class CustomItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom=16
        outRect.top=16
        outRect.left=32
        outRect.right=32
    }
}
