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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class StartupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository:
    UserPreferencesRepository,
    private val cloudBackupRepository:
    CloudBackupRepository
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
                initialValue =
                    authRepository.currentUser
            )


    /*
     * --------------------------------------------------
     * CLOUD SYNCHRONIZATION
     * --------------------------------------------------
     *
     * Whenever the authenticated Firebase user changes,
     * that user's Room data is synchronized with Firestore.
     *
     * The user ID is emitted only after synchronization
     * has completed successfully.
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
                         * Only mark synchronization as
                         * completed when the repository
                         * reports success.
                         */

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
                started = SharingStarted.Eagerly,
                initialValue = null
            )


    /*
     * --------------------------------------------------
     * CURRENT USER ASSESSMENT STATE
     * --------------------------------------------------
     *
     * The assessment state is resolved only after cloud
     * synchronization has completed successfully.
     *
     * The persisted preference is read once with first()
     * so a temporary/default emission cannot cause
     * MainActivity to select the wrong startup destination.
     *
     * null  = still resolving
     * false = assessment has not been completed
     * true  = assessment has been completed
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

                            if (
                                syncedUserId != user.uid
                            ) {

                                flowOf<Boolean?>(null)

                            } else {

                                flow {

                                    val completed =
                                        userPreferencesRepository
                                            .hasCompletedAssessment(
                                                userId = user.uid
                                            )
                                            .first()

                                    emit(
                                        completed
                                    )
                                }
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

