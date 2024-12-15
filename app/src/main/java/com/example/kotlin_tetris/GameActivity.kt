package com.example.kotlin_tetris

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var gameLogic: GameLogic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val gameView = findViewById<GameView>(R.id.gameView)
        gameLogic = GameLogic(20, 10)
        gameView.gameLogic = gameLogic

        gameLogic.spawnTetromino(
            Tetromino(TetrominoType.I, arrayOf(intArrayOf(1, 1, 1, 1)))
        )
    }
}
