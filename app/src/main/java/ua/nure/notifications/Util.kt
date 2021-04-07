package ua.nure.notifications

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


fun getCurrentUnixTime(): Long = System.currentTimeMillis() / 1000


class SpacingItemDecoration(private val verticalSpaceHigh: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = verticalSpaceHigh
    }
}