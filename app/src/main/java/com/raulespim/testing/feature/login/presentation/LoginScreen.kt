package com.raulespim.testing.feature.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.raulespim.testing.feature.login.data.repository.LoginRepositoryImpl
import com.raulespim.testing.feature.login.domain.usecase.ValidateCredentialsUseCase
import com.raulespim.testing.feature.welcome.presentation.WelcomeScreen
import com.raulespim.testing.ui.theme.TestingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val state by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {

            TextField(
                value = state.username,
                onValueChange = { viewModel.onIntent(LoginIntent.UpdateUsername(it)) },
                placeholder = {
                    Text(text = "Your Username")
                },
                label = {
                    Text(text = "Username")
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = state.password,
                onValueChange = { viewModel.onIntent(LoginIntent.UpdatePassword(it)) },
                placeholder = {
                    Text(text = "Your Password")
                },
                label = {
                    Text(text = "Password")
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage!!,
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                enabled = state.isButtonEnabled,
                onClick = {
                    viewModel.onIntent(LoginIntent.Submit)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )

            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                } else {
                    Text(text = "Login")
                }
            }

        }

        if (state.isLoggedIn) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.onIntent(LoginIntent.DismissWelcome) },
                sheetState = sheetState,
                content = {
                    WelcomeScreen()
                }
            )
        }
    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {

    val repository = LoginRepositoryImpl()
    val validateCredentialsUseCase = ValidateCredentialsUseCase(repository)
    val viewModel = LoginViewModel(validateCredentialsUseCase)

    TestingTheme {
        Surface {
            LoginScreen(viewModel)
        }
    }
}