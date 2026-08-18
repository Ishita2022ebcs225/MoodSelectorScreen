package com.example.moodselector.presentations.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val user: FirebaseUser? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            AuthUiState(
                isAuthenticated =
                    authRepository.currentUser != null,

                user =
                    authRepository.currentUser
            )
        )

    val uiState: StateFlow<AuthUiState> =
        _uiState.asStateFlow()


    /*
     * --------------------------------------------------
     * EMAIL / PASSWORD LOGIN
     * --------------------------------------------------
     */

    fun signInWithEmail(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {

        val cleanEmail =
            email.trim()

        if (cleanEmail.isBlank()) {

            setError(
                "Please enter your email address."
            )

            return
        }

        if (password.isBlank()) {

            setError(
                "Please enter your password."
            )

            return
        }

        viewModelScope.launch {

            _uiState.value =
                _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null
                )

            val result =
                authRepository.signIn(
                    email = cleanEmail,
                    password = password
                )

            result.fold(

                onSuccess = { user ->

                    handleAuthenticationSuccess(
                        user = user,
                        onSuccess = onSuccess
                    )
                },

                onFailure = { exception ->

                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            errorMessage =
                                authenticationErrorMessage(
                                    exception
                                )
                        )
                }
            )
        }
    }


    /*
     * --------------------------------------------------
     * EMAIL / PASSWORD REGISTRATION
     * --------------------------------------------------
     */

    fun createAccount(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit
    ) {

        val cleanName =
            name.trim()

        val cleanEmail =
            email.trim()

        if (cleanName.isBlank()) {

            setError(
                "Please enter your name."
            )

            return
        }

        if (cleanEmail.isBlank()) {

            setError(
                "Please enter your email address."
            )

            return
        }

        if (password.length < 6) {

            setError(
                "Your password must be at least 6 characters."
            )

            return
        }

        if (password != confirmPassword) {

            setError(
                "The passwords do not match."
            )

            return
        }

        viewModelScope.launch {

            _uiState.value =
                _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null
                )

            val result =
                authRepository.signUp(
                    name = cleanName,
                    email = cleanEmail,
                    password = password
                )

            result.fold(

                onSuccess = { user ->

                    handleAuthenticationSuccess(
                        user = user,
                        onSuccess = onSuccess
                    )
                },

                onFailure = { exception ->

                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            errorMessage =
                                authenticationErrorMessage(
                                    exception
                                )
                        )
                }
            )
        }
    }


    /*
     * --------------------------------------------------
     * GOOGLE SIGN-IN
     * --------------------------------------------------
     */

    fun signInWithGoogle(
        idToken: String,
        onSuccess: () -> Unit
    ) {

        if (idToken.isBlank()) {

            setError(
                "Google sign-in could not be completed."
            )

            return
        }

        viewModelScope.launch {

            _uiState.value =
                _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null
                )

            val result =
                authRepository.signInWithGoogle(
                    idToken
                )

            result.fold(

                onSuccess = { user ->

                    handleAuthenticationSuccess(
                        user = user,
                        onSuccess = onSuccess
                    )
                },

                onFailure = { exception ->

                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            errorMessage =
                                authenticationErrorMessage(
                                    exception
                                )
                        )
                }
            )
        }
    }


    /*
     * --------------------------------------------------
     * AUTHENTICATION SUCCESS
     * --------------------------------------------------
     */

    private fun handleAuthenticationSuccess(
        user: FirebaseUser,
        onSuccess: () -> Unit
    ) {

        _uiState.value =
            AuthUiState(
                isLoading = false,
                isAuthenticated = true,
                user = user,
                errorMessage = null
            )

        onSuccess()
    }


    /*
     * --------------------------------------------------
     * SIGN OUT
     * --------------------------------------------------
     */

    fun signOut() {

        authRepository.signOut()

        _uiState.value =
            AuthUiState()
    }


    /*
     * --------------------------------------------------
     * CLEAR ERROR
     * --------------------------------------------------
     */

    fun clearError() {

        _uiState.value =
            _uiState.value.copy(
                errorMessage = null
            )
    }


    private fun setError(
        message: String
    ) {

        _uiState.value =
            _uiState.value.copy(
                errorMessage = message
            )
    }


    /*
     * --------------------------------------------------
     * USER-FRIENDLY FIREBASE ERRORS
     * --------------------------------------------------
     */

    private fun authenticationErrorMessage(
        exception: Throwable
    ): String {

        return when {

            exception.message
                ?.contains(
                    "password is invalid",
                    ignoreCase = true
                ) == true ->

                "The email or password is incorrect."

            exception.message
                ?.contains(
                    "no user record",
                    ignoreCase = true
                ) == true ->

                "No account was found with this email."

            exception.message
                ?.contains(
                    "email address is already in use",
                    ignoreCase = true
                ) == true ->

                "An account already exists with this email."

            exception.message
                ?.contains(
                    "badly formatted",
                    ignoreCase = true
                ) == true ->

                "Please enter a valid email address."

            exception.message
                ?.contains(
                    "network",
                    ignoreCase = true
                ) == true ->

                "Please check your internet connection."

            else ->
                exception.message
                    ?: "Something went wrong. Please try again."
        }
    }
}

