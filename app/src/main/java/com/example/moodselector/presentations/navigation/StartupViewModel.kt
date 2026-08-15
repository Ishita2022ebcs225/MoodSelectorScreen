package com.example.moodselector.presentations.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.preferences.UserPreferencesRepository
import com.example.moodselector.domain.repository.AuthRepository
import com.example.moodselector.domain.repository.CloudBackupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StartupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val cloudBackupRepository: CloudBackupRepository
) : ViewModel() {

    /*
     * --------------------------------------------------
     * CURRENT USER
     * --------------------------------------------------
     */

    val currentUser =
        authRepository
            .authState
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = authRepository.currentUser
            )


    /*
     * --------------------------------------------------
     * CLOUD SYNCHRONIZATION
     * --------------------------------------------------
     *
     * Whenever the authenticated Firebase user changes,
     * that user's Room data is synchronized with
     * Firestore.
     */

    private val cloudSyncCompleted:
            StateFlow<String?> =
        authRepository
            .authState
            .distinctUntilChanged { oldUser, newUser ->
                oldUser?.uid == newUser?.uid
            }
            .flatMapLatest { user ->

                if (user == null) {

                    flowOf<String?>(null)

                } else {

                    flow<String?> {

                        val result =
                            cloudBackupRepository
                                .syncUserData(
                                    userId = user.uid
                                )

                        /*
                         * Whether synchronization succeeds
                         * or fails, allow the application to
                         * continue using local Room data.
                         */

                        emit(user.uid)
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = null
            )


    /*
     * --------------------------------------------------
     * CURRENT USER ASSESSMENT STATE
     * --------------------------------------------------
     *
     * The assessment state is evaluated only after
     * synchronization has completed for the current user.
     */

    val hasCompletedAssessment:
            StateFlow<Boolean?> =
        authRepository
            .authState
            .flatMapLatest { user ->

                if (user == null) {

                    flowOf(false)

                } else {

                    cloudSyncCompleted
                        .flatMapLatest { syncedUserId ->

                            if (syncedUserId != user.uid) {

                                flowOf<Boolean?>(null)

                            } else {

                                userPreferencesRepository
                                    .hasCompletedAssessment(
                                        userId = user.uid
                                    )
                            }
                        }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
}

