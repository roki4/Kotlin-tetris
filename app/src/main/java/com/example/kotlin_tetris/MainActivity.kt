package com.example.kotlin_tetris

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStartGame = findViewById<Button>(R.id.btnStartGame)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnStartGame.setOnClickListener {
            // Переход на экран игры
            startActivity(Intent(this, GameActivity::class.java))
        }

        btnLogout.setOnClickListener {
            // Завершить сессию и вернуться на экран входа
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
