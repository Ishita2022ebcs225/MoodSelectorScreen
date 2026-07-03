package com.example.moodselector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assessment_results")
data class AssessmentResultEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val timestamp: Long,

    val phq9Score: Int,

    val gad7Score: Int,

    val diagnosisSummary: String
)