package com.example.moodselector.presentations.cbt.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.SelfCompassionReflectionCompletionEntity
import com.example.moodselector.domain.repository.AuthRepository
import com.example.moodselector.domain.repository.CBTDailyProgressRepository
import com.example.moodselector.domain.repository.CloudBackupRepository
import com.example.moodselector.domain.repository.SelfCompassionReflectionCompletionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SelfCompassionReflectionViewModel @Inject constructor(
    private val repository:
    SelfCompassionReflectionCompletionRepository,
    private val authRepository:
    AuthRepository,
    private val cloudBackupRepository:
    CloudBackupRepository,
    private val dailyProgressRepository:
    CBTDailyProgressRepository
) : ViewModel() {

    /*
     * --------------------------------------------------
     * CURRENT USER ID
     * --------------------------------------------------
     */

    private val userId: String?
        get() = authRepository.currentUser?.uid


    /*
     * ==================================================
     * SAVE SELF-COMPASSION REFLECTION COMPLETION
     * ==================================================
     *
     * A completion is saved only after the user has:
     *
     * 1. Described the situation
     * 2. Written what they would say to a friend
     * 3. Written what they can say to themselves
     * 4. Confirmed completion on the screen
     *
     * The screen is responsible for validating these
     * conditions before calling this function.
     */

    fun saveCompletion(
        situation: String,
        friendResponse: String,
        selfCompassionResponse: String,
        onSaved: () -> Unit = {}
    ) {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            val completion =
                SelfCompassionReflectionCompletionEntity(
                    userId =
                        currentUserId,

                    situation =
                        situation.trim(),

                    friendResponse =
                        friendResponse.trim(),

                    selfCompassionResponse =
                        selfCompassionResponse.trim(),

                    completedAt =
                        System.currentTimeMillis()
                )

            /*
             * --------------------------------------------------
             * SAVE COMPLETION LOCALLY
             * --------------------------------------------------
             */

            repository.saveCompletion(
                completion
            )

            /*
             * --------------------------------------------------
             * UPDATE DAILY CBT PROGRESS
             * --------------------------------------------------
             *
             * This completed exercise contributes exactly
             * one completion to today's CBT progress.
             */

            val currentDate =
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.US
                ).format(
                    Date()
                )

            dailyProgressRepository.incrementDailyCompletion(
                userId = currentUserId,
                date = currentDate
            )

            /*
             * --------------------------------------------------
             * CLOUD BACKUP
             * --------------------------------------------------
             *
             * The completion has already been saved locally.
             * Cloud backup remains best-effort.
             */

            cloudBackupRepository
                .backupUserData(
                    userId = currentUserId
                )

            onSaved()
        }
    }
}