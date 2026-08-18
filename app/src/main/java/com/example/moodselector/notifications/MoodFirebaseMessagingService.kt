package com.example.moodselector.notifications

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MoodFirebaseMessagingService :
    FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(
            "FCM",
            "FCM token: $token"
        )

        /*
         * If a user is currently authenticated, save the
         * refreshed FCM token against that user's UID.
         *
         * Firebase can refresh the token independently of
         * the authentication flow, so this complements the
         * token saving performed by FirebaseAuthRepositoryImpl.
         */

        val user =
            FirebaseAuth
                .getInstance()
                .currentUser

        if (
            user == null ||
            token.isBlank()
        ) {
            return
        }

        CoroutineScope(
            Dispatchers.IO
        ).launch {

            try {

                FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(user.uid)
                    .set(
                        mapOf(
                            "fcmToken" to token
                        ),
                        SetOptions.merge()
                    )
                    .await()

            } catch (exception: Exception) {

                Log.e(
                    "FCM",
                    "Failed to save refreshed FCM token.",
                    exception
                )
            }
        }
    }

    override fun onMessageReceived(
        remoteMessage: RemoteMessage
    ) {
        super.onMessageReceived(remoteMessage)

        Log.d(
            "FCM",
            "Message received: ${remoteMessage.messageId}"
        )

        val title =
            remoteMessage.notification?.title
                ?: remoteMessage.data["title"]
                ?: "HerMind"

        val body =
            remoteMessage.notification?.body
                ?: remoteMessage.data["body"]
                ?: "You have a new notification."

        NotificationHelper.showNotification(
            context = this,
            title = title,
            body = body
        )
    }
}
