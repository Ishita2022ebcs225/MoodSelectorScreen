package com.example.moodselector.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moodselector.data.local.dao.AssessmentResultDao
import com.example.moodselector.data.local.dao.JournalDao
import com.example.moodselector.data.local.dao.MoodDao
import com.example.moodselector.data.local.entity.AssessmentResultEntity
import com.example.moodselector.data.local.entity.JournalEntity
import com.example.moodselector.data.local.entity.MoodEntry

@Database(
    entities = [
        MoodEntry::class,
        JournalEntity::class,
        AssessmentResultEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moodDao(): MoodDao

    abstract fun journalDao(): JournalDao

    abstract fun assessmentResultDao(): AssessmentResultDao
}