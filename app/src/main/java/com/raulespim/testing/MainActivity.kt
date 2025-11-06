package com.raulespim.testing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.raulespim.testing.feature.login.data.repository.LoginRepositoryImpl
import com.raulespim.testing.feature.login.domain.usecase.ValidateCredentialsUseCase
import com.raulespim.testing.feature.login.presentation.LoginScreen
import com.raulespim.testing.feature.login.presentation.LoginViewModel
import com.raulespim.testing.ui.theme.TestingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = LoginRepositoryImpl()
        val validateCredentialsUseCase = ValidateCredentialsUseCase(repository)
        val viewModel = LoginViewModel(validateCredentialsUseCase)

        setContent {
            TestingTheme {
                Surface {
                    LoginScreen(viewModel)
                }
            }
        }
    }
}


