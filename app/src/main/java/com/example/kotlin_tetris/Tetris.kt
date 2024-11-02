package com.example.kotlin_tetris;

import kotlin.random.Random

const val BOARD_WIDTH = 10
const val BOARD_HEIGHT = 20

class TetrisGame {
    private val gameBoard = Array(BOARD_HEIGHT) { Array(BOARD_WIDTH) { 0 } }
    private val shapes = listOf(
        arrayOf(intArrayOf(1, 1), intArrayOf(1, 1)),  // Квадрат
        arrayOf(intArrayOf(1, 1, 1, 1)),              // Палка
        arrayOf(intArrayOf(0, 1, 1), intArrayOf(1, 1, 0)),  // S-фигура
        arrayOf(intArrayOf(1, 1, 0), intArrayOf(0, 1, 1)),  // Z-фигура
        arrayOf(intArrayOf(1, 1, 1), intArrayOf(0, 1, 0)),  // T-фигура
        arrayOf(intArrayOf(1, 1, 1), intArrayOf(1, 0, 0)),  // L-фигура
        arrayOf(intArrayOf(1, 1, 1), intArrayOf(0, 0, 1))   // J-фигура
    )

    fun renderBoard() {
        for (row in gameBoard) {
            for (cell in row) {
                print(if (cell == 0) "." else "#")
            }
            println()
        }
    }

    fun placePiece() {
        val shape = shapes[Random.nextInt(shapes.size)]
        val startX = Random.nextInt(BOARD_WIDTH - shape[0].size + 1)

        for (y in shape.indices) {
            for (x in shape[y].indices) {
                if (shape[y][x] == 1) {
                    gameBoard[y][startX + x] = 1
                }
            }
        }
    }

    fun isGameOver(): Boolean {
        return gameBoard[0].any { it != 0 }
    }
}

fun main() {
    println("Добро пожаловать в Tetris!")

    val tetrisGame = TetrisGame()
    tetrisGame.placePiece()
    tetrisGame.renderBoard()

    if (tetrisGame.isGameOver()) {
        println("Игра окончена!")
    }
}