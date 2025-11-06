package com.raulespim.testing.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulespim.testing.feature.login.domain.usecase.ValidateCredentialsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.raulespim.testing.core.common.utils.Result

class LoginViewModel(
    private val validateCredentialsUseCase: ValidateCredentialsUseCase
) : ViewModel() {

    val _isValidCredentials = MutableStateFlow(false)
    val isValidCredentials: StateFlow<Boolean> get() = _isValidCredentials.asStateFlow()

    fun isValidCredentials(username: String, password: String) {
        viewModelScope.launch {
            validateCredentialsUseCase.invoke(username, password).collect { result ->

            }
        }
    }

}