package com.example.moodselector.presentations.auth

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch


@Composable
fun ReauthenticationScreen(
    onReauthenticationSuccess: () -> Unit,
    onCancel: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {

    /*
     * --------------------------------------------------
     * STATE
     * --------------------------------------------------
     */

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }


    /*
     * --------------------------------------------------
     * AUTH STATE
     * --------------------------------------------------
     */

    val state by
    viewModel.uiState
        .collectAsStateWithLifecycle()


    /*
     * --------------------------------------------------
     * GOOGLE CREDENTIALS
     * --------------------------------------------------
     */

    val context =
        LocalContext.current

    val scope =
        rememberCoroutineScope()

    val credentialManager =
        CredentialManager.create(
            context
        )


    /*
     * --------------------------------------------------
     * THEME COLORS
     * --------------------------------------------------
     *
     * These colors come from the application's
     * MaterialTheme, so the screen automatically responds
     * to System default, Light, and Dark mode.
     */

    val colorScheme =
        MaterialTheme.colorScheme


    /*
     * --------------------------------------------------
     * SCREEN
     * --------------------------------------------------
     */

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    colorScheme.background
                )
    ) {

        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .navigationBarsPadding()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 36.dp
                    ),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.spacedBy(
                    16.dp
                )
        ) {

            /*
             * --------------------------------------------------
             * ICON
             * --------------------------------------------------
             */

            Box(

                modifier =
                    Modifier
                        .size(76.dp)
                        .clip(
                            CircleShape
                        )
                        .background(
                            colorScheme
                                .secondaryContainer
                        ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(

                    imageVector =
                        Icons.Default.Lock,

                    contentDescription =
                        null,

                    tint =
                        colorScheme
                            .secondary,

                    modifier =
                        Modifier.size(
                            38.dp
                        )
                )
            }


            /*
             * --------------------------------------------------
             * TITLE
             * --------------------------------------------------
             */

            Text(

                text =
                    "Verify your identity",

                color =
                    colorScheme
                        .onBackground,

                fontSize =
                    28.sp,

                fontWeight =
                    FontWeight.Bold
            )


            /*
             * --------------------------------------------------
             * DESCRIPTION
             * --------------------------------------------------
             */

            Text(

                text =
                    "For your security, please verify your identity " +
                            "before continuing with account deletion.",

                color =
                    colorScheme
                        .onSurfaceVariant,

                fontSize =
                    14.sp,

                lineHeight =
                    21.sp
            )


            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )


            /*
             * --------------------------------------------------
             * EMAIL
             * --------------------------------------------------
             */

            OutlinedTextField(

                value =
                    email,

                onValueChange = {

                    email = it

                    viewModel.clearError()
                },

                modifier =
                    Modifier.fillMaxWidth(),

                label = {
                    Text(
                        "Email"
                    )
                },

                singleLine =
                    true,

                shape =
                    RoundedCornerShape(
                        16.dp
                    )
            )


            /*
             * --------------------------------------------------
             * PASSWORD
             * --------------------------------------------------
             */

            OutlinedTextField(

                value =
                    password,

                onValueChange = {

                    password = it

                    viewModel.clearError()
                },

                modifier =
                    Modifier.fillMaxWidth(),

                label = {
                    Text(
                        "Password"
                    )
                },

                singleLine =
                    true,

                trailingIcon = {

                    IconButton(
                        onClick = {

                            passwordVisible =
                                !passwordVisible
                        }
                    ) {

                        Icon(

                            imageVector =
                                if (passwordVisible) {

                                    Icons.Default.VisibilityOff

                                } else {

                                    Icons.Default.Visibility
                                },

                            contentDescription =
                                if (passwordVisible) {

                                    "Hide password"

                                } else {

                                    "Show password"
                                },

                            tint =
                                colorScheme
                                    .onSurfaceVariant
                        )
                    }
                },

                visualTransformation =
                    if (passwordVisible) {

                        VisualTransformation.None

                    } else {

                        PasswordVisualTransformation()
                    },

                shape =
                    RoundedCornerShape(
                        16.dp
                    )
            )


            /*
             * --------------------------------------------------
             * ERROR
             * --------------------------------------------------
             */

            if (
                state.errorMessage != null
            ) {

                Text(

                    text =
                        state.errorMessage!!,

                    color =
                        colorScheme.error,

                    fontSize =
                        13.sp,

                    modifier =
                        Modifier.fillMaxWidth()
                )
            }


            /*
             * --------------------------------------------------
             * EMAIL RE-AUTHENTICATION
             * --------------------------------------------------
             */

            Button(

                onClick = {

                    viewModel
                        .reauthenticateWithEmail(

                            email =
                                email,

                            password =
                                password,

                            onSuccess =
                                onReauthenticationSuccess
                        )
                },

                enabled =
                    !state.isLoading,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            54.dp
                        ),

                shape =
                    RoundedCornerShape(
                        18.dp
                    ),

                colors =
                    ButtonDefaults.buttonColors(

                        containerColor =
                            colorScheme.primary,

                        disabledContainerColor =
                            colorScheme
                                .primaryContainer
                    )
            ) {

                if (
                    state.isLoading
                ) {

                    CircularProgressIndicator(

                        modifier =
                            Modifier.size(
                                22.dp
                            ),

                        strokeWidth =
                            2.dp,

                        color =
                            colorScheme
                                .onPrimary
                    )

                } else {

                    Text(

                        text =
                            "Verify and Continue",

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }


            /*
             * --------------------------------------------------
             * DIVIDER
             * --------------------------------------------------
             */

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                HorizontalDivider(
                    modifier =
                        Modifier.weight(1f)
                )

                Text(

                    text =
                        "  or  ",

                    color =
                        colorScheme
                            .onSurfaceVariant,

                    fontSize =
                        12.sp
                )

                HorizontalDivider(
                    modifier =
                        Modifier.weight(1f)
                )
            }


            /*
             * --------------------------------------------------
             * GOOGLE RE-AUTHENTICATION
             * --------------------------------------------------
             */

            OutlinedButton(

                onClick = {

                    scope.launch {

                        reauthenticateWithGoogle(

                            context =
                                context,

                            credentialManager =
                                credentialManager,

                            onIdToken = { token ->

                                viewModel
                                    .reauthenticateWithGoogle(

                                        idToken =
                                            token,

                                        onSuccess =
                                            onReauthenticationSuccess
                                    )
                            },

                            onError = { _ ->

                                /*
                                 * Do not display a stale Google
                                 * cancellation/error message here.
                                 */
                                viewModel.clearError()
                            }
                        )
                    }
                },

                enabled =
                    !state.isLoading,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            54.dp
                        ),

                shape =
                    RoundedCornerShape(
                        18.dp
                    )
            ) {

                Text(

                    text =
                        "Verify with Google",

                    color =
                        colorScheme
                            .onSurface,

                    fontWeight =
                        FontWeight.SemiBold
                )
            }


            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )


            /*
             * --------------------------------------------------
             * CANCEL
             * --------------------------------------------------
             */

            TextButton(

                onClick =
                    onCancel,

                enabled =
                    !state.isLoading
            ) {

                Text(

                    text =
                        "Cancel",

                    color =
                        colorScheme.primary,

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }
    }
}


/*
 * ======================================================
 * GOOGLE RE-AUTHENTICATION
 * ======================================================
 */

private suspend fun reauthenticateWithGoogle(
    context: Context,
    credentialManager: CredentialManager,
    onIdToken: (String) -> Unit,
    onError: (String) -> Unit
) {

    try {

        val googleIdOption =
            GetGoogleIdOption.Builder()

                .setServerClientId(
                    context.getString(
                        com.example.moodselector.R.string
                            .default_web_client_id
                    )
                )

                /*
                 * The user is already authenticated.
                 *
                 * Allowing both authorized and other
                 * Google accounts lets the user choose
                 * the Google account associated with
                 * their Firebase account.
                 */

                .setFilterByAuthorizedAccounts(
                    false
                )

                .build()


        val request =
            GetCredentialRequest.Builder()
                .addCredentialOption(
                    googleIdOption
                )
                .build()


        val result =
            credentialManager.getCredential(

                context =
                    context,

                request =
                    request
            )


        val credential =
            result.credential


        if (
            credential.type ==
            GoogleIdTokenCredential
                .TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {

            val googleCredential =
                GoogleIdTokenCredential
                    .createFrom(
                        credential.data
                    )

            onIdToken(
                googleCredential.idToken
            )

        } else {

            onError(
                "Google re-authentication could not be completed."
            )
        }

    } catch (
        exception: GetCredentialException
    ) {

        onError(
            exception.message
                ?: "Google re-authentication was cancelled."
        )
    }
}