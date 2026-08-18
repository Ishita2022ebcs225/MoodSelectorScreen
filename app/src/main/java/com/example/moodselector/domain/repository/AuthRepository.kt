package com.example.moodselector.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val currentUser: FirebaseUser?

    val authState: Flow<FirebaseUser?>

    suspend fun signIn(
        email: String,
        password: String
    ): Result<FirebaseUser>

    suspend fun signInWithGoogle(
        idToken: String
    ): Result<FirebaseUser>

    suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Result<FirebaseUser>

    fun signOut()

    suspend fun sendPasswordResetEmail(
        email: String
    ): Result<Unit>
}