package com.example.kotlin_tetris

enum class TetrominoType(val color: Int) {
    I(android.graphics.Color.CYAN),
    J(android.graphics.Color.BLUE),
    L(android.graphics.Color.rgb(255, 165, 0)),
    O(android.graphics.Color.YELLOW),
    S(android.graphics.Color.GREEN),
    T(android.graphics.Color.MAGENTA),
    Z(android.graphics.Color.RED)
}

class Tetromino(val type: TetrominoType, var shape: Array<IntArray>) {
    var x: Int = 0
    var y: Int = 0

    fun rotate() {
        val rows = shape.size
        val cols = shape[0].size
        val rotatedShape = Array(cols) { IntArray(rows) }

        for (i in shape.indices) {
            for (j in shape[i].indices) {
                rotatedShape[j][rows - 1 - i] = shape[i][j]
            }
        }

        shape = rotatedShape
    }




}
