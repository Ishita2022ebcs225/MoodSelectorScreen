package com.example.moodselector.data.local.converter

import androidx.room.TypeConverter
import com.example.moodselector.domain.assessment.model.AssessmentSeverity

class AssessmentSeverityConverter {

    @TypeConverter
    fun fromSeverity(
        severity: AssessmentSeverity
    ): String {
        return severity.name
    }

    @TypeConverter
    fun toSeverity(
        value: String
    ): AssessmentSeverity {
        return AssessmentSeverity.valueOf(value)
    }
}