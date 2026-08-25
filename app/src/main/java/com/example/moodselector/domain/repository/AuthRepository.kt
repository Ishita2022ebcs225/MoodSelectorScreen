package com.example.moodselector.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

/*
 * --------------------------------------------------
 * GOOGLE SIGN-IN RESULT
 * --------------------------------------------------
 *
 * Contains both the authenticated Firebase user and
 * whether Firebase reports that this authentication
 * created a new account.
 *
 * This distinction is required because Google
 * authentication is used for both:
 *
 *      New Google account
 *
 * and
 *
 *      Existing Google account
 *
 * Firebase provides this information through
 * AuthResult.additionalUserInfo?.isNewUser.
 */

data class GoogleSignInResult(
    val user: FirebaseUser,
    val isNewUser: Boolean
)


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
     *
     * Returns both the authenticated Firebase user and
     * whether Firebase reports that this is a newly
     * created account.
     */

    suspend fun signInWithGoogle(
        idToken: String
    ): Result<GoogleSignInResult>


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