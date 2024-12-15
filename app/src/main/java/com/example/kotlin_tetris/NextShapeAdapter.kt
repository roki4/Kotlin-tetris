package com.example.kotlin_tetris

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class NextShapeAdapter(
    private val context: Context,
    private var shape: Array<IntArray>
) : BaseAdapter() {

    fun updateShape(newShape: Array<IntArray>) {
        shape = newShape
        notifyDataSetChanged()
    }

    override fun getCount(): Int = 16 // 4x4

    override fun getItem(position: Int): Any = position

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView = convertView as? ImageView ?: ImageView(context)
        val row = position / 4
        val col = position % 4

        val isPartOfShape = row < shape.size && col < shape[row].size && shape[row][col] == 1
        imageView.setBackgroundColor(
            if (isPartOfShape) android.graphics.Color.BLUE
            else android.graphics.Color.BLACK
        )
        imageView.layoutParams = ViewGroup.LayoutParams(30, 30)
        return imageView
    }
}
