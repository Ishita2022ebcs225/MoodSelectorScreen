package com.example.moodselector.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moodselector.data.local.dao.MoodDao
import com.example.moodselector.data.local.dao.JournalDao
import com.example.moodselector.data.local.entity.MoodEntry
import com.example.moodselector.data.local.entity.JournalEntity

@Database(
    entities = [
        MoodEntry::class,
        JournalEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class MoodDatabase : RoomDatabase() {

    abstract fun moodDao(): MoodDao
    abstract fun journalDao(): JournalDao

    companion object {

        @Volatile
        private var INSTANCE: MoodDatabase? = null

        fun getDatabase(context: Context): MoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoodDatabase::class.java,
                    "mood_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}