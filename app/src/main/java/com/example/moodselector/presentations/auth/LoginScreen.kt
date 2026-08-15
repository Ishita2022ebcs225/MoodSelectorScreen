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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
private val PaleLavender = Color(0xFFF7F5FF)
private val SoftRose = Color(0xFFFFEEF4)
private val Rose = Color(0xFFE88BA5)
private val TextPrimary = Color(0xFF292638)
private val TextSecondary = Color(0xFF777282)
private val Background = Color(0xFFFAF9FD)

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

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val context =
        LocalContext.current

    val scope =
        rememberCoroutineScope()

    val credentialManager =
        CredentialManager.create(context)

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Background)
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

            Box(
                modifier =
                    Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .background(
                            SoftLavender
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

            Text(
                text = "Welcome to HerMind",

                color =
                    TextPrimary,

                fontSize =
                    28.sp,

                fontWeight =
                    FontWeight.Bold
            )

            Text(
                text =
                    "A safe space to understand yourself,\n" +
                            "build healthier patterns, and grow.",

                color =
                    TextSecondary,

                fontSize =
                    14.sp,

                lineHeight =
                    21.sp
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

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
                    Text("Email")
                },

                singleLine = true,

                shape =
                    RoundedCornerShape(16.dp)
            )

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
                    Text("Password")
                },

                singleLine = true,

                visualTransformation =
                    PasswordVisualTransformation(),

                shape =
                    RoundedCornerShape(16.dp)
            )

            if (state.errorMessage != null) {

                Text(
                    text =
                        state.errorMessage!!,

                    color =
                        Color(0xFFB44A5A),

                    fontSize =
                        13.sp,

                    modifier =
                        Modifier.fillMaxWidth()
                )
            }

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
                            Color.White
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
                        TextSecondary,

                    fontSize =
                        12.sp
                )

                HorizontalDivider(
                    modifier =
                        Modifier.weight(1f)
                )
            }

            OutlinedButton(
                onClick = {

                    scope.launch {

                        signInWithGoogle(
                            context =
                                context,

                            credentialManager =
                                credentialManager,

                            onIdToken =
                                { token ->

                                    viewModel.signInWithGoogle(
                                        idToken =
                                            token,

                                        onSuccess =
                                            onLoginSuccess
                                    )
                                },

                            onError =
                                { message ->

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
                        .height(54.dp),

                shape =
                    RoundedCornerShape(18.dp)
            ) {

                Text(
                    text =
                        "Continue with Google",

                    color =
                        TextPrimary,

                    fontWeight =
                        FontWeight.SemiBold
                )
            }

            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text =
                        "Don't have an account? ",

                    color =
                        TextSecondary,

                    fontSize =
                        13.sp
                )

                androidx.compose.material3.TextButton(
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
            GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
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