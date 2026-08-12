package com.example.moodselector.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moodselector.data.local.converter.AssessmentSeverityConverter
import com.example.moodselector.data.local.dao.AssessmentResultDao
import com.example.moodselector.data.local.dao.CBTActivityCompletionDao
import com.example.moodselector.data.local.dao.JournalDao
import com.example.moodselector.data.local.dao.MoodDao
import com.example.moodselector.data.local.dao.ScheduledCBTActivityDao
import com.example.moodselector.data.local.entity.AssessmentResultEntity
import com.example.moodselector.data.local.entity.CBTActivityCompletionEntity
import com.example.moodselector.data.local.entity.JournalEntity
import com.example.moodselector.data.local.entity.MoodEntry
import com.example.moodselector.data.local.entity.ScheduledCBTActivityEntity

@Database(
    entities = [
        MoodEntry::class,
        JournalEntity::class,
        AssessmentResultEntity::class,
        CBTActivityCompletionEntity::class,
        ScheduledCBTActivityEntity::class
    ],
    version = 6,
    exportSchema = false
)
@TypeConverters(
    AssessmentSeverityConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moodDao(): MoodDao

    abstract fun journalDao(): JournalDao

    abstract fun assessmentResultDao(): AssessmentResultDao

    abstract fun cbtActivityCompletionDao(): CBTActivityCompletionDao

    abstract fun scheduledCBTActivityDao(): ScheduledCBTActivityDao
}

