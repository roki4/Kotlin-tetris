package com.example.kotlin_tetris

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Инициализация кнопок
        val moveLeftButton = findViewById<Button>(R.id.moveLeftButton)
        val moveRightButton = findViewById<Button>(R.id.moveRightButton)
        val moveDownButton = findViewById<Button>(R.id.moveDownButton)
        val rotateButton = findViewById<Button>(R.id.rotateButton)

        // Установка слушателей для кнопок
        moveLeftButton.setOnClickListener {
            // Логика для перемещения фигуры влево
            moveLeft()
        }

        moveRightButton.setOnClickListener {
            // Логика для перемещения фигуры вправо
            moveRight()
        }

        moveDownButton.setOnClickListener {
            // Логика для ускоренного спуска фигуры вниз
            moveDown()
        }

        rotateButton.setOnClickListener {
            // Логика для поворота фигуры
            rotate()
        }
    }

    private fun moveLeft() {
        // Здесь будет логика для перемещения фигуры влево
    }

    private fun moveRight() {
        // Здесь будет логика для перемещения фигуры вправо
    }

    private fun moveDown() {
        // Здесь будет логика для ускоренного спуска фигуры вниз
    }

    private fun rotate() {
        // Здесь будет логика для поворота фигуры
    }
}
