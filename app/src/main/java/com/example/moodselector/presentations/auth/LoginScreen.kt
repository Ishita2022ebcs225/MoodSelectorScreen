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
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.ui.graphics.Color
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

private val Lavender = Color(0xFF6C63FF)
private val SoftLavender = Color(0xFFEDEBFF)
private val Rose = Color(0xFFE88BA5)

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val context =
        LocalContext.current

    val scope =
        rememberCoroutineScope()

    val credentialManager =
        CredentialManager.create(context)


    /*
     * ==================================================
     * THEME-SENSITIVE COLORS
     * ==================================================
     */

    val backgroundColor =
        MaterialTheme.colorScheme.background

    val primaryTextColor =
        MaterialTheme.colorScheme.onBackground

    val secondaryTextColor =
        MaterialTheme.colorScheme.onSurfaceVariant

    val errorColor =
        MaterialTheme.colorScheme.error


    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    backgroundColor
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
                Arrangement.spacedBy(16.dp)
        ) {


            /*
             * ==================================================
             * APP ICON
             * ==================================================
             */

            Box(
                modifier =
                    Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme
                                .colorScheme
                                .surfaceVariant
                        ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Favorite,

                    contentDescription =
                        null,

                    tint =
                        Rose,

                    modifier =
                        Modifier.size(38.dp)
                )
            }


            /*
             * ==================================================
             * TITLE
             * ==================================================
             */

            Text(
                text =
                    "Welcome to HerMind",

                color =
                    primaryTextColor,

                fontSize =
                    28.sp,

                fontWeight =
                    FontWeight.Bold
            )


            /*
             * ==================================================
             * DESCRIPTION
             * ==================================================
             */

            Text(
                text =
                    "A safe space to understand yourself,\n" +
                            "build healthier patterns, and grow.",

                color =
                    secondaryTextColor,

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
             * ==================================================
             * EMAIL
             * ==================================================
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
                        text =
                            "Email"
                    )
                },

                singleLine =
                    true,

                shape =
                    RoundedCornerShape(16.dp)
            )


            /*
             * ==================================================
             * PASSWORD
             * ==================================================
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
                        text =
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
                                MaterialTheme
                                    .colorScheme
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
                    RoundedCornerShape(16.dp)
            )


            /*
             * ==================================================
             * ERROR MESSAGE
             * ==================================================
             */

            if (state.errorMessage != null) {

                Text(
                    text =
                        state.errorMessage!!,

                    color =
                        errorColor,

                    fontSize =
                        13.sp,

                    modifier =
                        Modifier.fillMaxWidth()
                )
            }


            /*
             * ==================================================
             * EMAIL SIGN-IN BUTTON
             * ==================================================
             */

            Button(
                onClick = {

                    viewModel.signInWithEmail(
                        email =
                            email,

                        password =
                            password,

                        onSuccess =
                            onLoginSuccess
                    )
                },

                enabled =
                    !state.isLoading,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(54.dp),

                shape =
                    RoundedCornerShape(18.dp),

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            Lavender,

                        disabledContainerColor =
                            SoftLavender
                    )
            ) {

                if (state.isLoading) {

                    CircularProgressIndicator(
                        modifier =
                            Modifier.size(22.dp),

                        strokeWidth =
                            2.dp,

                        color =
                            MaterialTheme
                                .colorScheme
                                .onPrimary
                    )

                } else {

                    Text(
                        text =
                            "Sign In",

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }


            /*
             * ==================================================
             * DIVIDER
             * ==================================================
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
                        secondaryTextColor,

                    fontSize =
                        12.sp
                )

                HorizontalDivider(
                    modifier =
                        Modifier.weight(1f)
                )
            }


            /*
             * ==================================================
             * GOOGLE SIGN-IN
             * ==================================================
             */

            OutlinedButton(
                onClick = {

                    /*
                     * Remove any previous error before
                     * beginning a new Google sign-in attempt.
                     */
                    viewModel.clearError()

                    scope.launch {

                        signInWithGoogle(
                            context =
                                context,

                            credentialManager =
                                credentialManager,

                            onIdToken =
                                { token ->

                                    /*
                                     * Firebase authentication
                                     * now takes control of the
                                     * authentication state.
                                     */
                                    viewModel.signInWithGoogle(
                                        idToken =
                                            token,

                                        onSuccess =
                                            onLoginSuccess
                                    )
                                },

                            onError =
                                { message ->

                                    /*
                                     * Only set an error when the
                                     * Google credential process
                                     * actually fails.
                                     */
                                    if (
                                        message.isNotBlank()
                                    ) {

                                        viewModel.setGoogleError(
                                            message
                                        )
                                    }
                                }
                        )
                    }
                },

                enabled =
                    !state.isLoading,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(54.dp),

                shape =
                    RoundedCornerShape(18.dp)
            ) {

                Text(
                    text =
                        "Continue with Google",

                    color =
                        MaterialTheme
                            .colorScheme
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
             * ==================================================
             * REGISTER
             * ==================================================
             */

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text =
                        "Don't have an account? ",

                    color =
                        secondaryTextColor,

                    fontSize =
                        13.sp
                )

                TextButton(
                    onClick =
                        onRegisterClick
                ) {

                    Text(
                        text =
                            "Create one",

                        color =
                            Lavender,

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
        }
    }
}


/*
 * ======================================================
 * GOOGLE SIGN-IN
 * ======================================================
 */

private suspend fun signInWithGoogle(
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


            /*
             * A valid credential has been obtained.
             *
             * Do not set or clear any authentication
             * error here. AuthViewModel handles the Firebase
             * authentication result.
             */
            onIdToken(
                googleCredential.idToken
            )

        } else {

            onError(
                "Google sign-in could not be completed."
            )
        }

    } catch (
        exception: GetCredentialException
    ) {

        onError(
            exception.message
                ?: "Google sign-in was cancelled."
        )
    }
}