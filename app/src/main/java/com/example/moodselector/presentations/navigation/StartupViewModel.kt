package com.example.moodselector.presentations.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.preferences.UserPreferencesRepository
import com.example.moodselector.domain.repository.AuthRepository
import com.example.moodselector.domain.repository.CloudBackupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class StartupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val cloudBackupRepository: CloudBackupRepository,
    private val userPreferencesRepository:
    UserPreferencesRepository
) : ViewModel() {

    /*
     * --------------------------------------------------
     * CURRENT USER
     * --------------------------------------------------
     */

    val currentUser:
            StateFlow<com.google.firebase.auth.FirebaseUser?> =
        authRepository
            .authState
            .stateIn(
                scope = viewModelScope,
                started =
                    SharingStarted.WhileSubscribed(
                        5000
                    ),
                initialValue =
                    authRepository.currentUser
            )


    /*
     * --------------------------------------------------
     * CLOUD SYNCHRONIZATION
     * --------------------------------------------------
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

                        if (result.isSuccess) {

                            emit(
                                user.uid
                            )

                        } else {

                            emit(null)
                        }
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started =
                    SharingStarted.Eagerly,
                initialValue = null
            )


    /*
     * --------------------------------------------------
     * STARTUP READINESS
     * --------------------------------------------------
     *
     * A signed-in user is considered ready only after
     * their cloud synchronization has completed
     * successfully.
     */

    val isReady:
            StateFlow<Boolean> =
        authRepository
            .authState
            .flatMapLatest { user ->

                if (user == null) {

                    flowOf(true)

                } else {

                    cloudSyncCompleted
                        .flatMapLatest {
                                syncedUserId ->

                            flowOf(
                                syncedUserId ==
                                        user.uid
                            )
                        }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started =
                    SharingStarted.WhileSubscribed(
                        5000
                    ),
                initialValue = false
            )


    /*
     * --------------------------------------------------
     * NEW USER STATUS
     * --------------------------------------------------
     *
     * This is intentionally separate from assessment
     * completion.
     *
     * The new-user flag is checked only after the
     * authenticated user's cloud synchronization has
     * completed successfully.
     *
     * New email accounts and newly created Google
     * accounts are marked by AuthViewModel.
     *
     * Existing accounts are not marked as new and
     * therefore proceed directly to the main app.
     */

    val isNewUser:
            StateFlow<Boolean> =
        authRepository
            .authState
            .flatMapLatest { user ->

                if (user == null) {

                    flowOf(false)

                } else {

                    cloudSyncCompleted
                        .flatMapLatest {
                                syncedUserId ->

                            if (
                                syncedUserId !=
                                user.uid
                            ) {

                                flowOf(false)

                            } else {

                                userPreferencesRepository
                                    .isNewUser(
                                        user.uid
                                    )
                            }
                        }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started =
                    SharingStarted.WhileSubscribed(
                        5000
                    ),
                initialValue = false
            )


    /*
     * --------------------------------------------------
     * NEW USER RESOLUTION STATE
     * --------------------------------------------------
     *
     * This is different from isNewUser.
     *
     * isNewUser tells us the actual result.
     *
     * isNewUserResolved tells MainActivity that the
     * result has been obtained and it is now safe to
     * create the NavHost with the correct destination.
     *
     * This prevents a new user from briefly seeing
     * MoodInsights before being redirected to the
     * assessment onboarding screen.
     */

    val isNewUserResolved:
            StateFlow<Boolean> =
        authRepository
            .authState
            .flatMapLatest { user ->

                if (user == null) {

                    flowOf(false)

                } else {

                    cloudSyncCompleted
                        .flatMapLatest {
                                syncedUserId ->

                            if (
                                syncedUserId !=
                                user.uid
                            ) {

                                flowOf(false)

                            } else {

                                userPreferencesRepository
                                    .isNewUser(
                                        user.uid
                                    )
                                    .map {
                                        true
                                    }
                            }
                        }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started =
                    SharingStarted.WhileSubscribed(
                        5000
                    ),
                initialValue = false
            )
}

