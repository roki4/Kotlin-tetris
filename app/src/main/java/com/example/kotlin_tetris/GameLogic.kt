package com.example.kotlin_tetris

class GameLogic(val rows: Int, val cols: Int) {
    val field: Array<IntArray> = Array(rows) { IntArray(cols) { 0 } }
    var currentTetromino: Tetromino? = null
    var nextTetromino: Tetromino? = null

    fun spawnTetromino(tetromino: Tetromino? = null) {
        currentTetromino = tetromino ?: nextTetromino ?: generateRandomTetromino()
        nextTetromino = generateRandomTetromino() // Генерируем новую следующую фигуру

        currentTetromino?.let {
            it.x = cols / 2 - it.shape[0].size / 2
            it.y = 0
        }

        if (!canPlaceTetromino(currentTetromino!!, currentTetromino!!.x, currentTetromino!!.y)) {
            throw IllegalStateException("Game Over")
        }
    }

    fun moveTetromino(dx: Int, dy: Int): Boolean {
        clearCurrentTetromino() // Удалить текущую фигуру с поля
        val tetromino = currentTetromino ?: return false
        val newX = tetromino.x + dx
        val newY = tetromino.y + dy

        if (canPlaceTetromino(tetromino, newX, newY)) {
            tetromino.x = newX
            tetromino.y = newY
            drawCurrentTetromino() // Отрисовать фигуру на новом месте
            return true
        } else if (dy > 0) {
            placeTetromino(tetromino)
            return false
        }

        return false
    }


    fun clearCurrentTetromino() {
        currentTetromino?.let { tetromino ->
            for (i in tetromino.shape.indices) {
                for (j in tetromino.shape[i].indices) {
                    if (tetromino.shape[i][j] == 1) {
                        val x = tetromino.x + j
                        val y = tetromino.y + i
                        if (x in 0 until cols && y in 0 until rows) {
                            field[y][x] = 0 // Убираем временную фигуру
                        }
                    }
                }
            }
        }
    }
    fun getNextTetrominoShape(): Array<IntArray> {
        return nextTetromino?.shape ?: arrayOf()
    }


    fun drawCurrentTetromino() {
        currentTetromino?.let { tetromino ->
            for (i in tetromino.shape.indices) {
                for (j in tetromino.shape[i].indices) {
                    if (tetromino.shape[i][j] == 1) {
                        val x = tetromino.x + j
                        val y = tetromino.y + i
                        if (x in 0 until cols && y in 0 until rows) {
                            field[y][x] = tetromino.type.color // Используем цвет фигуры
                        }
                    }
                }
            }
        }
    }


    fun rotateTetromino() {
        val tetromino = currentTetromino ?: return

        // Очистите текущее состояние фигуры
        clearCurrentTetromino()

        // Сохраняем исходное состояние для возможного отката
        val originalShape = tetromino.shape.map { it.copyOf() }.toTypedArray()
        val originalX = tetromino.x
        val originalY = tetromino.y

        // Поворачиваем фигуру
        tetromino.rotate()

        // Проверяем, можно ли разместить повернутую фигуру
        if (!canPlaceTetromino(tetromino, tetromino.x, tetromino.y)) {
            // Откатываем, если фигура не помещается
            tetromino.shape = originalShape
            tetromino.x = originalX
            tetromino.y = originalY
        }

        // Отрисовываем фигуру снова
        drawCurrentTetromino()
    }





    fun generateRandomTetromino(): Tetromino {
        val types = TetrominoType.values()
        val randomType = types.random()
        val shapes = when (randomType) {
            TetrominoType.I -> arrayOf(intArrayOf(1, 1, 1, 1))
            TetrominoType.J -> arrayOf(intArrayOf(1, 0, 0), intArrayOf(1, 1, 1))
            TetrominoType.L -> arrayOf(intArrayOf(0, 0, 1), intArrayOf(1, 1, 1))
            TetrominoType.O -> arrayOf(intArrayOf(1, 1), intArrayOf(1, 1))
            TetrominoType.S -> arrayOf(intArrayOf(0, 1, 1), intArrayOf(1, 1, 0))
            TetrominoType.T -> arrayOf(intArrayOf(0, 1, 0), intArrayOf(1, 1, 1))
            TetrominoType.Z -> arrayOf(intArrayOf(1, 1, 0), intArrayOf(0, 1, 1))
        }
        return Tetromino(randomType, shapes)
    }

    private fun canPlaceTetromino(tetromino: Tetromino, x: Int, y: Int): Boolean {
        for (i in tetromino.shape.indices) {
            for (j in tetromino.shape[i].indices) {
                if (tetromino.shape[i][j] == 1) {
                    val newX = x + j
                    val newY = y + i
                    // Проверяем границы игрового поля
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
        clearLines() // Убедитесь, что метод вызывается здесь
        currentTetromino = null
    }


    fun clearLines(): Int {
        var linesCleared = 0
        for (y in field.indices) {
            if (field[y].all { it != 0 }) {
                linesCleared++
                for (r in y downTo 1) {
                    field[r] = field[r - 1]
                }
                field[0] = IntArray(cols) { 0 }
            }
        }
        return linesCleared
    }



}
