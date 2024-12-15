package com.example.kotlin_tetris

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GameView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val paint = Paint()
    var gameLogic: GameLogic? = null

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        gameLogic?.let { game ->
            val cellSize = width / game.cols
            for (y in game.field.indices) {
                for (x in game.field[y].indices) {
                    paint.color = game.field[y][x]
                    if (paint.color != 0) {
                        canvas.drawRect(
                            (x * cellSize).toFloat(),
                            (y * cellSize).toFloat(),
                            ((x + 1) * cellSize).toFloat(),
                            ((y + 1) * cellSize).toFloat(),
                            paint
                        )
                    }
                }
            }
        }
    }
}
