package com.example.kotlin_tetris

import com.example.kotlin_tetris.DatabaseHelper
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Инициализация com.example.kotlin_tetris.database.DatabaseHelper
        val dbHelper = DatabaseHelper(this)

        // Замените userId на ID текущего пользователя
        val userId = 1

        // Получаем лучший результат и последние 10 игр
        val bestScore = dbHelper.getBestScore(userId)
        val recentScores = dbHelper.getLastScores(userId)

        // Форматируем результаты для отображения
        val formattedScores: List<String> = recentScores.map { score ->
            "Очки: ${score.first}, Уровень: ${score.second}"
        }

        // Отображаем лучший результат
        val tvBestScore = findViewById<TextView>(R.id.tvBestScore)
        tvBestScore.text = getString(R.string.best_score, bestScore)

        // Отображаем последние 10 игр
        val lvRecentScores = findViewById<ListView>(R.id.lvRecentScores)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, formattedScores)
        lvRecentScores.adapter = adapter
    }
}
