package com.example.moodselector.data.repository

import com.example.moodselector.domain.repository.AuthRepository
import com.example.moodselector.domain.repository.GoogleSignInResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseMessaging: FirebaseMessaging,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override val authState: Flow<FirebaseUser?> =
        callbackFlow {

            val listener =
                FirebaseAuth.AuthStateListener { auth ->

                    trySend(
                        auth.currentUser
                    )
                }

            firebaseAuth.addAuthStateListener(
                listener
            )

            awaitClose {
                firebaseAuth.removeAuthStateListener(
                    listener
                )
            }
        }


    /*
     * --------------------------------------------------
     * EMAIL / PASSWORD LOGIN
     * --------------------------------------------------
     *
     * Firebase authentication is the actual login
     * operation.
     *
     * FCM token synchronization is performed separately
     * and must never cause an otherwise successful login
     * to fail.
     */

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<FirebaseUser> {

        return try {

            val result =
                firebaseAuth
                    .signInWithEmailAndPassword(
                        email,
                        password
                    )
                    .await()

            val user =
                result.user
                    ?: return Result.failure(
                        IllegalStateException(
                            "Authentication succeeded but no user was returned."
                        )
                    )

            /*
             * FCM synchronization is secondary to
             * authentication.
             *
             * If it fails, the Firebase login should still
             * be considered successful.
             */
            try {

                saveFcmToken(
                    user
                )

            } catch (_: Exception) {
                // Do not fail authentication because FCM
                // token synchronization failed.
            }

            Result.success(
                user
            )

        } catch (e: Exception) {

            Result.failure(
                e
            )
        }
    }


    /*
     * --------------------------------------------------
     * GOOGLE SIGN-IN
     * --------------------------------------------------
     *
     * Firebase provides additionalUserInfo.isNewUser
     * when signInWithCredential() completes.
     *
     * This is important because Google authentication is
     * used for both:
     *
     *      1. Creating a new account
     *      2. Signing into an existing account
     *
     * The startup flow needs to distinguish those cases
     * so a newly created Google account can be shown the
     * assessment before entering the main application.
     *
     * FCM token synchronization must not cause a
     * successful Google authentication to fail.
     */

    override suspend fun signInWithGoogle(
        idToken: String
    ): Result<GoogleSignInResult> {

        return try {

            if (idToken.isBlank()) {

                return Result.failure(
                    IllegalArgumentException(
                        "Google sign-in could not be completed."
                    )
                )
            }

            val credential =
                GoogleAuthProvider.getCredential(
                    idToken,
                    null
                )

            val result =
                firebaseAuth
                    .signInWithCredential(
                        credential
                    )
                    .await()

            val user =
                result.user
                    ?: return Result.failure(
                        IllegalStateException(
                            "Google authentication succeeded but no user was returned."
                        )
                    )

            /*
             * Firebase determines whether this Google
             * authentication created a new Firebase
             * account.
             */

            val isNewUser =
                result
                    .additionalUserInfo
                    ?.isNewUser
                    ?: false

            /*
             * FCM synchronization is secondary to
             * authentication.
             */
            try {

                saveFcmToken(
                    user
                )

            } catch (_: Exception) {
                // Do not fail authentication because FCM
                // token synchronization failed.
            }

            Result.success(
                GoogleSignInResult(
                    user = user,
                    isNewUser = isNewUser
                )
            )

        } catch (e: Exception) {

            Result.failure(
                e
            )
        }
    }


    /*
     * --------------------------------------------------
     * EMAIL / PASSWORD REGISTRATION
     * --------------------------------------------------
     */

    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Result<FirebaseUser> {

        return try {

            val result =
                firebaseAuth
                    .createUserWithEmailAndPassword(
                        email,
                        password
                    )
                    .await()

            val user =
                result.user
                    ?: return Result.failure(
                        IllegalStateException(
                            "Account creation succeeded but no user was returned."
                        )
                    )

            /*
             * Save the user's name in the Firebase
             * Authentication profile.
             */

            val profileUpdates =
                UserProfileChangeRequest
                    .Builder()
                    .setDisplayName(name)
                    .build()

            user.updateProfile(
                profileUpdates
            ).await()

            /*
             * FCM synchronization is secondary to
             * account creation.
             */
            try {

                saveFcmToken(
                    user
                )

            } catch (_: Exception) {
                // Do not fail account creation because FCM
                // token synchronization failed.
            }

            Result.success(
                user
            )

        } catch (e: Exception) {

            Result.failure(
                e
            )
        }
    }


    /*
     * --------------------------------------------------
     * RE-AUTHENTICATION WITH EMAIL / PASSWORD
     * --------------------------------------------------
     *
     * Re-authenticates the currently signed-in Firebase
     * user without signing into a new account.
     *
     * This is required before sensitive operations such
     * as account deletion when Firebase determines that
     * the existing authentication is no longer recent.
     */

    override suspend fun reauthenticateWithEmail(
        email: String,
        password: String
    ): Result<FirebaseUser> {

        return try {

            val user =
                firebaseAuth.currentUser
                    ?: return Result.failure(
                        IllegalStateException(
                            "No authenticated user is available."
                        )
                    )

            val cleanEmail =
                email.trim()

            if (cleanEmail.isBlank()) {

                return Result.failure(
                    IllegalArgumentException(
                        "Please enter your email address."
                    )
                )
            }

            if (password.isBlank()) {

                return Result.failure(
                    IllegalArgumentException(
                        "Please enter your password."
                    )
                )
            }

            val credential =
                EmailAuthProvider.getCredential(
                    cleanEmail,
                    password
                )

            user
                .reauthenticate(
                    credential
                )
                .await()

            Result.success(
                user
            )

        } catch (e: Exception) {

            Result.failure(
                e
            )
        }
    }


    /*
     * --------------------------------------------------
     * RE-AUTHENTICATION WITH GOOGLE
     * --------------------------------------------------
     *
     * Re-authenticates the currently signed-in Firebase
     * user using a fresh Google ID token.
     */

    override suspend fun reauthenticateWithGoogle(
        idToken: String
    ): Result<FirebaseUser> {

        return try {

            val user =
                firebaseAuth.currentUser
                    ?: return Result.failure(
                        IllegalStateException(
                            "No authenticated user is available."
                        )
                    )

            if (idToken.isBlank()) {

                return Result.failure(
                    IllegalArgumentException(
                        "Google re-authentication could not be completed."
                    )
                )
            }

            val credential =
                GoogleAuthProvider.getCredential(
                    idToken,
                    null
                )

            user
                .reauthenticate(
                    credential
                )
                .await()

            Result.success(
                user
            )

        } catch (e: Exception) {

            Result.failure(
                e
            )
        }
    }


    /*
     * --------------------------------------------------
     * SIGN OUT
     * --------------------------------------------------
     */

    override fun signOut() {

        firebaseAuth.signOut()
    }


    /*
     * --------------------------------------------------
     * PASSWORD RESET
     * --------------------------------------------------
     */

    override suspend fun sendPasswordResetEmail(
        email: String
    ): Result<Unit> {

        return try {

            firebaseAuth
                .sendPasswordResetEmail(
                    email
                )
                .await()

            Result.success(
                Unit
            )

        } catch (e: Exception) {

            Result.failure(
                e
            )
        }
    }


    /*
     * --------------------------------------------------
     * DELETE FIREBASE AUTHENTICATION ACCOUNT
     * --------------------------------------------------
     *
     * Firebase requires recent authentication before
     * allowing sensitive operations such as account
     * deletion.
     *
     * Application data stored in Room, DataStore, and
     * Firestore backup is handled separately by the
     * corresponding data repositories.
     */

    override suspend fun deleteAccount(): Result<Unit> {

        return try {

            val user =
                firebaseAuth.currentUser
                    ?: return Result.failure(
                        IllegalStateException(
                            "No authenticated user is available."
                        )
                    )

            user
                .delete()
                .await()

            Result.success(
                Unit
            )

        } catch (
            e: FirebaseAuthRecentLoginRequiredException
        ) {

            Result.failure(
                IllegalStateException(
                    "Please sign in again before deleting your account.",
                    e
                )
            )

        } catch (e: Exception) {

            Result.failure(
                e
            )
        }
    }


    /*
     * --------------------------------------------------
     * FCM TOKEN
     * --------------------------------------------------
     *
     * Store the current device's FCM token against the
     * authenticated Firebase user.
     *
     * This operation is deliberately kept separate from
     * authentication success. A failure here must not
     * cause sign-in or account creation to fail.
     *
     * merge() is used so existing user data in the
     * Firestore document is preserved.
     */

    private suspend fun saveFcmToken(
        user: FirebaseUser
    ) {

        val token =
            firebaseMessaging
                .token
                .await()

        if (token.isBlank()) {
            return
        }

        firestore
            .collection("users")
            .document(user.uid)
            .set(
                mapOf(
                    "fcmToken" to token
                ),
                com.google.firebase.firestore.SetOptions.merge()
            )
            .await()
    }
}

