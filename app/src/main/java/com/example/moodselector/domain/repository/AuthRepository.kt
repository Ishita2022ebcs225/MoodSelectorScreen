package com.example.moodselector.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val currentUser: FirebaseUser?

    val authState: Flow<FirebaseUser?>

    /*
     * --------------------------------------------------
     * EMAIL / PASSWORD LOGIN
     * --------------------------------------------------
     */

    suspend fun signIn(
        email: String,
        password: String
    ): Result<FirebaseUser>


    /*
     * --------------------------------------------------
     * GOOGLE SIGN-IN
     * --------------------------------------------------
     */

    suspend fun signInWithGoogle(
        idToken: String
    ): Result<FirebaseUser>


    /*
     * --------------------------------------------------
     * EMAIL / PASSWORD REGISTRATION
     * --------------------------------------------------
     */

    suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Result<FirebaseUser>


    /*
     * --------------------------------------------------
     * EMAIL / PASSWORD RE-AUTHENTICATION
     * --------------------------------------------------
     *
     * Used when Firebase requires recent authentication
     * before performing a sensitive operation such as
     * account deletion.
     */

    suspend fun reauthenticateWithEmail(
        email: String,
        password: String
    ): Result<FirebaseUser>


    /*
     * --------------------------------------------------
     * GOOGLE RE-AUTHENTICATION
     * --------------------------------------------------
     *
     * Used when the authenticated account was created
     * or last authenticated through Google.
     */

    suspend fun reauthenticateWithGoogle(
        idToken: String
    ): Result<FirebaseUser>


    /*
     * --------------------------------------------------
     * SIGN OUT
     * --------------------------------------------------
     */

    fun signOut()


    /*
     * --------------------------------------------------
     * PASSWORD RESET
     * --------------------------------------------------
     */

    suspend fun sendPasswordResetEmail(
        email: String
    ): Result<Unit>


    /*
     * --------------------------------------------------
     * DELETE ACCOUNT
     * --------------------------------------------------
     */

    suspend fun deleteAccount(): Result<Unit>
}