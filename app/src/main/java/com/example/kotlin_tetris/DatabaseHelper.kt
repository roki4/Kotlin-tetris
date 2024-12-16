package com.example.kotlin_tetris

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "tetris.db", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY, username TEXT, password TEXT)")
        db.execSQL("CREATE TABLE scores (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, score INTEGER, level INTEGER, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)")
    }

    fun saveScore(userId: Int, score: Int, level: Int) {
        val db = writableDatabase
        val query = "INSERT INTO scores (user_id, score, level) VALUES (?, ?, ?)"
        db.execSQL(query, arrayOf(userId, score, level))
        db.close()
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS scores")
        onCreate(db)
    }

    fun getBestScore(userId: Int): Int {
        val db = readableDatabase
        val query = "SELECT MAX(score) FROM scores WHERE user_id = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
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
        val query = "SELECT score, level FROM scores WHERE user_id = ? ORDER BY timestamp DESC LIMIT 10"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        val results = mutableListOf<Pair<Int, Int>>()
        while (cursor.moveToNext()) {
            results.add(cursor.getInt(0) to cursor.getInt(1))
        }
        cursor.close()
        db.close()
        return results
    }




    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS scores")
        onCreate(db)
    }
}