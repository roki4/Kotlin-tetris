package com.example.kotlin_tetris

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "tetris.db", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        // Создание таблицы пользователей
        db.execSQL(
            """
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL
            )
            """.trimIndent()
        )

        // Создание таблицы результатов
        db.execSQL(
            """
            CREATE TABLE scores (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                score INTEGER NOT NULL,
                level INTEGER NOT NULL,
                timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(id)
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS users")
            db.execSQL("DROP TABLE IF EXISTS scores")
            onCreate(db)
        }
    }

    fun saveScore(userId: Int, score: Int, level: Int) {
        val db = writableDatabase
        db.execSQL(
            "INSERT INTO scores (user_id, score, level) VALUES (?, ?, ?)",
            arrayOf(userId, score, level)
        )
        db.close()
    }

    fun getBestScore(userId: Int): Int {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT MAX(score) FROM scores WHERE user_id = ?",
            arrayOf(userId.toString())
        )
        var bestScore = 0
        if (cursor.moveToFirst()) {
            bestScore = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return bestScore
    }

    fun getLastScores(userId: Int): List<Pair<Int, Int>> {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT score, level FROM scores WHERE user_id = ? ORDER BY timestamp DESC LIMIT 10",
            arrayOf(userId.toString())
        )
        val results = mutableListOf<Pair<Int, Int>>()
        while (cursor.moveToNext()) {
            results.add(cursor.getInt(0) to cursor.getInt(1))
        }
        cursor.close()
        db.close()
        return results
    }
}
