package com.example.moodselector.presentations.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RegisterScreen(
    onBackClick: () -> Unit,
    onRegistrationSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {

    /*
     * ----------------------------------------------------------
     * THEME COLORS
     * ----------------------------------------------------------
     *
     * These values come from MaterialTheme so the screen
     * automatically responds to the app's System / Light /
     * Dark theme selection.
     */

    val colorScheme =
        MaterialTheme.colorScheme

    val backgroundColor =
        colorScheme.background

    val textPrimary =
        colorScheme.onBackground

    val textSecondary =
        colorScheme.onSurfaceVariant

    val primaryColor =
        colorScheme.primary

    val errorColor =
        colorScheme.error


    /*
     * ----------------------------------------------------------
     * FORM STATE
     * ----------------------------------------------------------
     */

    var name by rememberSaveable {
        mutableStateOf("")
    }

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var confirmPassword by rememberSaveable {
        mutableStateOf("")
    }

    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var confirmPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }


    /*
     * ----------------------------------------------------------
     * AUTH STATE
     * ----------------------------------------------------------
     */

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val isLoading =
        uiState.isLoading

    val errorMessage =
        uiState.errorMessage


    /*
     * ----------------------------------------------------------
     * VALIDATION
     * ----------------------------------------------------------
     */

    val passwordsMatch =
        password == confirmPassword

    val canRegister =
        name.isNotBlank() &&
                email.isNotBlank() &&
                password.length >= 6 &&
                confirmPassword.isNotBlank() &&
                passwordsMatch &&
                !isLoading


    /*
     * ----------------------------------------------------------
     * UI
     * ----------------------------------------------------------
     */

    Scaffold(
        containerColor =
            backgroundColor
    ) { paddingValues ->

        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .navigationBarsPadding()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 20.dp
                    ),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.spacedBy(14.dp)
        ) {

            /*
             * --------------------------------------------------
             * BACK BUTTON
             * --------------------------------------------------
             */

            IconButton(

                onClick =
                    onBackClick,

                modifier =
                    Modifier.align(
                        Alignment.Start
                    )
            ) {

                Icon(

                    imageVector =
                        Icons.AutoMirrored.Filled.ArrowBack,

                    contentDescription =
                        "Back",

                    tint =
                        textPrimary
                )
            }


            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )


            /*
             * --------------------------------------------------
             * TITLE
             * --------------------------------------------------
             */

            Text(

                text =
                    "Create your account",

                color =
                    textPrimary,

                fontSize =
                    28.sp,

                fontWeight =
                    FontWeight.Bold
            )


            Text(

                text =
                    "Create your HerMind account to keep your progress safe and accessible.",

                color =
                    textSecondary,

                fontSize =
                    14.sp,

                lineHeight =
                    20.sp
            )


            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )


            /*
             * --------------------------------------------------
             * NAME
             * --------------------------------------------------
             */

            OutlinedTextField(

                value =
                    name,

                onValueChange = {
                    name = it
                },

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine =
                    true,

                label = {
                    Text(
                        text = "Name"
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Person,

                        contentDescription =
                            null
                    )
                },

                shape =
                    RoundedCornerShape(
                        18.dp
                    )
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
                },

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine =
                    true,

                label = {
                    Text(
                        text = "Email"
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Email,

                        contentDescription =
                            null
                    )
                },

                shape =
                    RoundedCornerShape(
                        18.dp
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
                },

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine =
                    true,

                label = {
                    Text(
                        text = "Password"
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Lock,

                        contentDescription =
                            null
                    )
                },

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
                                }
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
                        18.dp
                    )
            )


            Text(

                text =
                    "Password must be at least 6 characters.",

                modifier =
                    Modifier.fillMaxWidth(),

                color =
                    textSecondary,

                fontSize =
                    12.sp
            )


            /*
             * --------------------------------------------------
             * CONFIRM PASSWORD
             * --------------------------------------------------
             */

            OutlinedTextField(

                value =
                    confirmPassword,

                onValueChange = {
                    confirmPassword = it
                },

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine =
                    true,

                label = {
                    Text(
                        text = "Confirm password"
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Lock,

                        contentDescription =
                            null
                    )
                },

                trailingIcon = {

                    IconButton(
                        onClick = {
                            confirmPasswordVisible =
                                !confirmPasswordVisible
                        }
                    ) {

                        Icon(

                            imageVector =
                                if (confirmPasswordVisible) {
                                    Icons.Default.VisibilityOff
                                } else {
                                    Icons.Default.Visibility
                                },

                            contentDescription =
                                if (confirmPasswordVisible) {
                                    "Hide password"
                                } else {
                                    "Show password"
                                }
                        )
                    }
                },

                visualTransformation =
                    if (confirmPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },

                isError =
                    confirmPassword.isNotEmpty() &&
                            !passwordsMatch,

                shape =
                    RoundedCornerShape(
                        18.dp
                    )
            )


            if (
                confirmPassword.isNotEmpty() &&
                !passwordsMatch
            ) {

                Text(

                    text =
                        "Passwords do not match.",

                    modifier =
                        Modifier.fillMaxWidth(),

                    color =
                        errorColor,

                    fontSize =
                        12.sp
                )
            }


            /*
             * --------------------------------------------------
             * FIREBASE ERROR
             * --------------------------------------------------
             */

            if (!errorMessage.isNullOrBlank()) {

                Text(

                    text =
                        errorMessage,

                    modifier =
                        Modifier.fillMaxWidth(),

                    color =
                        errorColor,

                    fontSize =
                        13.sp,

                    lineHeight =
                        18.sp
                )
            }


            /*
             * --------------------------------------------------
             * CREATE ACCOUNT
             * --------------------------------------------------
             */

            Button(

                onClick = {

                    viewModel.createAccount(

                        name =
                            name.trim(),

                        email =
                            email.trim(),

                        password =
                            password,

                        confirmPassword =
                            confirmPassword,

                        onSuccess =
                            onRegistrationSuccess
                    )
                },

                enabled =
                    canRegister,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(54.dp),

                shape =
                    RoundedCornerShape(
                        18.dp
                    ),

                colors =
                    ButtonDefaults.buttonColors(

                        containerColor =
                            primaryColor,

                        disabledContainerColor =
                            colorScheme.surfaceVariant,

                        disabledContentColor =
                            textSecondary
                    )
            ) {

                if (isLoading) {

                    CircularProgressIndicator(

                        modifier =
                            Modifier.size(
                                22.dp
                            ),

                        color =
                            colorScheme.onPrimary,

                        strokeWidth =
                            2.dp
                    )

                } else {

                    Text(

                        text =
                            "Create Account",

                        fontWeight =
                            FontWeight.Bold,

                        fontSize =
                            15.sp
                    )
                }
            }


            /*
             * --------------------------------------------------
             * LOGIN
             * --------------------------------------------------
             */

            TextButton(
                onClick =
                    onLoginClick
            ) {

                Text(

                    text =
                        "Already have an account? Sign in",

                    color =
                        primaryColor,

                    fontWeight =
                        FontWeight.SemiBold
                )
            }


            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )
        }
    }
}