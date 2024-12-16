package com.example.kotlin_tetris

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val dbHelper = DatabaseHelper(this)
        val userId = 1 // Замените на текущий ID пользователя

        // Получаем лучший результат и последние 10 игр
        val bestScore = dbHelper.getBestScore(userId)
        val recentScores = dbHelper.getLastScores(userId)
        val formattedScores = recentScores.map { "Очки: ${it.first}, Уровень: ${it.second}" }

        // Отображаем лучший результат
        val tvBestScore = findViewById<TextView>(R.id.tvBestScore)
        tvBestScore.text = "Лучший результат: $bestScore"

        // Отображаем последние 10 игр
        val lvRecentScores = findViewById<ListView>(R.id.lvRecentScores)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, formattedScores)
        lvRecentScores.adapter = adapter
    }
}
