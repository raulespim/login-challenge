package com.raulespim.testing.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulespim.testing.feature.login.domain.usecase.ValidateCredentialsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.raulespim.testing.core.common.utils.Result
import kotlinx.coroutines.flow.update

class LoginViewModel(
    private val validateCredentialsUseCase: ValidateCredentialsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> get() = _state.asStateFlow()

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.UpdateUsername -> {
                _state.update { it.copy(username = intent.value, errorMessage = null) }
                updateButtonState()
            }
            is LoginIntent.UpdatePassword -> {
                _state.update { it.copy(password = intent.value, errorMessage = null) }
                updateButtonState()
            }
            LoginIntent.Submit -> { login() }
            LoginIntent.DismissWelcome -> { _state.update { it.copy(isLoggedIn = false) } }
        }
    }

    private fun updateButtonState() {
        _state.update {
            it.copy(isButtonEnabled = it.username.isNotBlank() && it.password.isNotBlank())
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            validateCredentialsUseCase.invoke(_state.value.username, _state.value.password).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isLoggedIn = result.data,
                                errorMessage = if (!result.data) {
                                    "Username or Password are invalid, please try again."
                                } else null
                            )
                        }
                    }
                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "Something went wrong."
                            )
                        }
                    }
                }

            }
        }
    }

}

sealed class LoginIntent {
    data class UpdateUsername(val value: String) : LoginIntent()
    data class UpdatePassword(val value: String) : LoginIntent()
    data object Submit : LoginIntent()
    data object DismissWelcome : LoginIntent()
}