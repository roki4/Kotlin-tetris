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

class Tetromino(val type: TetrominoType, val shape: Array<IntArray>) {
    var x: Int = 0
    var y: Int = 0

    fun rotate() {
        val size = shape.size
        val rotatedShape = Array(size) { IntArray(size) }
        for (i in shape.indices) {
            for (j in shape[i].indices) {
                rotatedShape[j][size - 1 - i] = shape[i][j]
            }
        }
        for (i in shape.indices) shape[i] = rotatedShape[i]
    }
}
