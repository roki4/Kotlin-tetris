package com.example.kotlin_tetris

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var gameLogic: GameLogic
    private var score: Int = 0
    private var level: Int = 1
    private var delay: Long = 500 // Начальная скорость падения (мс)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val gameView = findViewById<GameView>(R.id.gameView)
        val tvScore = findViewById<TextView>(R.id.tvScore)
        val tvLevel = findViewById<TextView>(R.id.tvLevel)
        gameLogic = GameLogic(20, 10)
        gameView.gameLogic = gameLogic

        // Первая фигура
        gameLogic.spawnTetromino(gameLogic.generateRandomTetromino())
        updateNextShapeView()

        // Кнопки управления
        val btnLeft = findViewById<Button>(R.id.btnLeft)
        val btnRight = findViewById<Button>(R.id.btnRight)
        val btnDown = findViewById<Button>(R.id.btnDown)
        val btnRotate = findViewById<Button>(R.id.btnRotate)

        btnLeft.setOnClickListener {
            gameLogic.moveTetromino(-1, 0)
            updateView()
        }
        btnRight.setOnClickListener {
            gameLogic.moveTetromino(1, 0)
            updateView()
        }
        btnDown.setOnClickListener {
            gameLogic.moveTetromino(0, 1)
            updateView()
        }
        btnRotate.setOnClickListener {
            gameLogic.rotateTetromino()
            updateView()
        }

        startGameLoop(tvScore, tvLevel)
    }

    private fun updateView() {
        findViewById<GameView>(R.id.gameView).invalidate()
    }

    private fun updateNextShapeView() {
        val nextShapeView = findViewById<GameView>(R.id.nextShapeView)
        val nextShapeLogic = GameLogic(4, 4)
        val nextTetrominoShape = gameLogic.nextTetromino?.shape ?: arrayOf()

        for (i in nextTetrominoShape.indices) {
            for (j in nextTetrominoShape[i].indices) {
                nextShapeLogic.field[i][j] = if (nextTetrominoShape[i][j] == 1) {
                    gameLogic.nextTetromino?.type?.color ?: 0
                } else {
                    0
                }
            }
        }

        nextShapeView.gameLogic = nextShapeLogic
        nextShapeView.invalidate()
    }

    private fun startGameLoop(tvScore: TextView, tvLevel: TextView) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                try {
                    if (gameLogic.moveTetromino(0, 1)) {
                        updateView()
                    } else {
                        val linesCleared = gameLogic.clearLines()
                        if (linesCleared > 0) {
                            score += linesCleared * 100
                            if (score >= level * 500) { // Порог повышения уровня
                                level++
                                delay = maxOf(100, delay - 50) // Ускоряем падение
                            }
                        }
                        tvScore.text = "Очки: $score"
                        tvLevel.text = "Уровень: $level"

                        gameLogic.spawnTetromino()
                        updateNextShapeView()
                    }
                    handler.postDelayed(this, delay)
                } catch (e: IllegalStateException) {
                    showGameOver()
                }
            }
        })
    }

    private fun showGameOver() {
        runOnUiThread {
            Toast.makeText(this, "Игра окончена! Ваш счет: $score", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
