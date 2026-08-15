package com.example.moodselector.presentations.assessment.questionnaire

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.assessment.provider.AssessmentDefinitionProvider
import com.example.moodselector.data.local.entity.AssessmentResultEntity
import com.example.moodselector.data.preferences.UserPreferencesRepository
import com.example.moodselector.domain.assessment.model.AssessmentType
import com.example.moodselector.domain.assessment.utils.GAD7SeverityCalculator
import com.example.moodselector.domain.assessment.utils.PHQ9SeverityCalculator
import com.example.moodselector.domain.repository.AssessmentRepository
import com.example.moodselector.domain.repository.AuthRepository
import com.example.moodselector.domain.repository.CloudBackupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssessmentViewModel @Inject constructor(
    private val assessmentDefinitionProvider:
    AssessmentDefinitionProvider,

    private val assessmentRepository:
    AssessmentRepository,

    private val userPreferencesRepository:
    UserPreferencesRepository,

    private val authRepository:
    AuthRepository,

    private val cloudBackupRepository:
    CloudBackupRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            AssessmentUiState()
        )

    val uiState:
            StateFlow<AssessmentUiState> =
        _uiState.asStateFlow()


    /*
     * --------------------------------------------------
     * CURRENT USER ID
     * --------------------------------------------------
     */

    private val userId: String?
        get() =
            authRepository
                .currentUser
                ?.uid


    init {

        loadAssessment(
            AssessmentType.PHQ9
        )
    }


    private fun loadAssessment(
        type: AssessmentType
    ) {

        val definition =
            assessmentDefinitionProvider
                .getAssessmentDefinition(type)

        _uiState.update {

            it.copy(

                assessment =
                    definition,

                currentAssessmentType =
                    type,

                currentQuestionIndex =
                    0,

                selectedAnswers =
                    emptyMap()
            )
        }
    }


    fun selectAnswer(
        questionId: Int,
        score: Int
    ) {

        val updatedAnswers =
            _uiState.value
                .selectedAnswers
                .toMutableMap()

        updatedAnswers[
            questionId
        ] = score

        _uiState.update {

            it.copy(
                selectedAnswers =
                    updatedAnswers
            )
        }
    }


    fun nextQuestion() {

        val assessment =
            _uiState.value
                .assessment
                ?: return

        val currentIndex =
            _uiState.value
                .currentQuestionIndex

        if (
            currentIndex <
            assessment.questions.lastIndex
        ) {

            _uiState.update {

                it.copy(
                    currentQuestionIndex =
                        currentIndex + 1
                )
            }

        } else {

            completeCurrentAssessment()
        }
    }


    fun previousQuestion() {

        val currentIndex =
            _uiState.value
                .currentQuestionIndex

        if (
            currentIndex > 0
        ) {

            _uiState.update {

                it.copy(
                    currentQuestionIndex =
                        currentIndex - 1
                )
            }
        }
    }


    private fun completeCurrentAssessment() {

        val assessment =
            _uiState.value
                .assessment
                ?: return

        val score =
            _uiState.value
                .selectedAnswers
                .values
                .sum()


        when (
            assessment.type
        ) {

            AssessmentType.PHQ9 -> {

                val severity =
                    PHQ9SeverityCalculator
                        .getSeverity(
                            score
                        )

                _uiState.update {

                    it.copy(

                        phq9Score =
                            score,

                        phq9Severity =
                            severity,

                        phq9Completed =
                            true
                    )
                }

                loadAssessment(
                    AssessmentType.GAD7
                )
            }


            AssessmentType.GAD7 -> {

                val severity =
                    GAD7SeverityCalculator
                        .getSeverity(
                            score
                        )

                val updatedState =
                    _uiState.value.copy(

                        gad7Score =
                            score,

                        gad7Severity =
                            severity,

                        gad7Completed =
                            true,

                        isCompleted =
                            true
                    )

                _uiState.value =
                    updatedState


                val diagnosisSummary =
                    "Depression: ${updatedState.phq9Severity}, Anxiety: ${updatedState.gad7Severity}"


                viewModelScope.launch {

                    val currentUserId =
                        userId
                            ?: return@launch


                    /*
                     * ----------------------------------------------
                     * SAVE RESULT FOR CURRENT USER
                     * ----------------------------------------------
                     */

                    assessmentRepository
                        .saveResult(

                            AssessmentResultEntity(

                                userId =
                                    currentUserId,

                                timestamp =
                                    System.currentTimeMillis(),

                                phq9Score =
                                    updatedState.phq9Score,

                                phq9Severity =
                                    updatedState.phq9Severity,

                                gad7Score =
                                    updatedState.gad7Score,

                                gad7Severity =
                                    updatedState.gad7Severity,

                                diagnosisSummary =
                                    diagnosisSummary
                            )
                        )


                    /*
                     * ----------------------------------------------
                     * MARK ASSESSMENT COMPLETE FOR CURRENT USER
                     * ----------------------------------------------
                     */

                    userPreferencesRepository
                        .setAssessmentCompleted(

                            userId =
                                currentUserId,

                            completed =
                                true
                        )


                    /*
                     * ----------------------------------------------
                     * BACK UP UPDATED USER DATA
                     * ----------------------------------------------
                     *
                     * The assessment has already been saved
                     * successfully to Room.
                     *
                     * Firestore backup is attempted afterward.
                     * A cloud failure does not affect the local
                     * assessment result or mark the assessment
                     * as unsuccessful.
                     */

                    cloudBackupRepository
                        .backupUserData(
                            userId =
                                currentUserId
                        )
                }
            }
        }
    }


    fun restartAssessment() {

        _uiState.value =
            AssessmentUiState()

        loadAssessment(
            AssessmentType.PHQ9
        )
    }
}