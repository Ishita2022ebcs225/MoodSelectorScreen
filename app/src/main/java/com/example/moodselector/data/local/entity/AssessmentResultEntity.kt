package com.example.moodselector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moodselector.domain.assessment.model.AssessmentSeverity

@Entity(tableName = "assessment_results")
data class AssessmentResultEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val timestamp: Long,

    val phq9Score: Int,

    val phq9Severity: AssessmentSeverity,

    val gad7Score: Int,

    val gad7Severity: AssessmentSeverity,

    val diagnosisSummary: String
)