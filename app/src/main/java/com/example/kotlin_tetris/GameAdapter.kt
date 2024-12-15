package com.example.kotlin_tetris

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class GameAdapter(
    private val context: Context,
    private val gameField: Array<IntArray>,
    private val currentShapeColor: Int
) : BaseAdapter() {

    override fun getCount(): Int = gameField.size * gameField[0].size

    override fun getItem(position: Int): Any {
        val row = position / gameField[0].size
        val col = position % gameField[0].size
        return gameField[row][col]
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView = convertView as? ImageView ?: ImageView(context)
        val row = position / gameField[0].size
        val col = position % gameField[0].size

        // Устанавливаем цвет клеток
        imageView.setBackgroundColor(
            when (gameField[row][col]) {
                0 -> android.graphics.Color.BLACK   // Пустая клетка
                1 -> android.graphics.Color.GRAY    // Закрепленная фигура
                2 -> currentShapeColor              // Летящая фигура
                else -> android.graphics.Color.BLACK
            }
        )


        // Устанавливаем размер клетки и черную границу
        imageView.layoutParams = ViewGroup.LayoutParams(100, 100)
        imageView.setPadding(1, 1, 1, 1)
        imageView.setBackgroundResource(android.R.color.black)

        return imageView
    }
}
