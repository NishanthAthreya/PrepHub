package com.example.prephub.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Quiz entity.
 */
@Entity(tableName = "quizzes", primaryKeys = ["name", "folder"])
data class Quiz(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "folder") val folder: String,
    @ColumnInfo(name = "questionsAndAnswers") val questionsAndAnswers: Map<String, String>
)