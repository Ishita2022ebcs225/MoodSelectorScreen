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
                            true
                    )


                /*
                 * --------------------------------------------------
                 * SAVE RESULT AND COMPLETE LOCALLY
                 * --------------------------------------------------
                 *
                 * The Room result is saved first.
                 *
                 * Once the local result has been successfully saved,
                 * isCompleted is published immediately so the
                 * questionnaire can navigate to the results screen.
                 *
                 * User preferences and cloud backup happen after
                 * completion is published and therefore cannot block
                 * navigation to the results screen.
                 */

                viewModelScope.launch {

                    val currentUserId =
                        userId
                            ?: return@launch


                    val diagnosisSummary =
                        "Depression: ${updatedState.phq9Severity}, Anxiety: ${updatedState.gad7Severity}"


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
                     * PUBLISH COMPLETION IMMEDIATELY
                     * ----------------------------------------------
                     *
                     * Room has successfully persisted the result.
                     *
                     * AssessmentQuestionnaireScreen observes
                     * isCompleted and navigates to AssessmentResults.
                     *
                     * Nothing related to preferences or cloud backup
                     * occurs before this state change.
                     */

                    _uiState.update {

                        updatedState.copy(
                            isCompleted =
                                true
                        )
                    }


                    /*
                     * ----------------------------------------------
                     * MARK ASSESSMENT COMPLETE FOR CURRENT USER
                     * ----------------------------------------------
                     *
                     * This is local persistence and is intentionally
                     * performed after the results screen navigation
                     * signal has already been published.
                     */

                    try {

                        userPreferencesRepository
                            .setAssessmentCompleted(

                                userId =
                                    currentUserId,

                                completed =
                                    true
                            )

                    } catch (
                        _: Exception
                    ) {

                        /*
                         * The assessment result is already persisted
                         * in Room and completion has already been
                         * published to the UI.
                         *
                         * Therefore a preference update failure must
                         * not prevent the results screen from opening.
                         */
                    }


                    /*
                     * ----------------------------------------------
                     * BACK UP UPDATED USER DATA
                     * ----------------------------------------------
                     *
                     * Cloud backup is performed only after the local
                     * result has been saved and completion has been
                     * published.
                     *
                     * A cloud backup failure must not prevent the
                     * assessment results screen from opening.
                     */

                    try {

                        cloudBackupRepository
                            .backupUserData(
                                userId =
                                    currentUserId
                            )

                    } catch (
                        _: Exception
                    ) {

                        /*
                         * Local assessment data has already been
                         * successfully saved. Therefore a cloud
                         * backup failure does not block the user from
                         * continuing.
                         */
                    }
                }
            }
        }
    }
}
