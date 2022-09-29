package cn.govast.gmusic.ui.components

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2021/8/29 18:13
// Description:
// Documentation:

class SpacesItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space
        }
    }
}