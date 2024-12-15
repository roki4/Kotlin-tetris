package com.example.kotlin_tetris

class GameLogic(val rows: Int, val cols: Int) {
    val field: Array<IntArray> = Array(rows) { IntArray(cols) { 0 } }
    var currentTetromino: Tetromino? = null
    var nextTetromino: Tetromino? = null

    fun spawnTetromino(tetromino: Tetromino) {
        currentTetromino = tetromino
        tetromino.x = cols / 2 - tetromino.shape.size / 2
        tetromino.y = 0
        if (!canPlaceTetromino(tetromino, tetromino.x, tetromino.y)) {
            throw IllegalStateException("Game Over")
        }
    }

    fun moveTetromino(dx: Int, dy: Int): Boolean {
        val tetromino = currentTetromino ?: return false
        val newX = tetromino.x + dx
        val newY = tetromino.y + dy
        if (canPlaceTetromino(tetromino, newX, newY)) {
            tetromino.x = newX
            tetromino.y = newY
            return true
        } else if (dy > 0) {
            placeTetromino(tetromino)
            return false
        }
        return false
    }

    fun rotateTetromino() {
        val tetromino = currentTetromino ?: return
        tetromino.rotate()
        if (!canPlaceTetromino(tetromino, tetromino.x, tetromino.y)) {
            tetromino.rotate() // Отменить ротацию, если нельзя разместить
            tetromino.rotate()
            tetromino.rotate()
        }
    }

    private fun canPlaceTetromino(tetromino: Tetromino, x: Int, y: Int): Boolean {
        for (i in tetromino.shape.indices) {
            for (j in tetromino.shape[i].indices) {
                if (tetromino.shape[i][j] == 1) {
                    val newX = x + j
                    val newY = y + i
                    if (newX !in 0 until cols || newY !in 0 until rows || field[newY][newX] != 0) {
                        return false
                    }
                }
            }
        }
        return true
    }

    private fun placeTetromino(tetromino: Tetromino) {
        for (i in tetromino.shape.indices) {
            for (j in tetromino.shape[i].indices) {
                if (tetromino.shape[i][j] == 1) {
                    val x = tetromino.x + j
                    val y = tetromino.y + i
                    if (x in 0 until cols && y in 0 until rows) {
                        field[y][x] = tetromino.type.color
                    }
                }
            }
        }
        clearLines()
        currentTetromino = null
    }

    private fun clearLines() {
        for (y in field.indices) {
            if (field[y].all { it != 0 }) {
                for (r in y downTo 1) {
                    field[r] = field[r - 1]
                }
                field[0] = IntArray(cols) { 0 }
            }
        }
    }
}
