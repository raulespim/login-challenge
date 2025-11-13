package com.raulespim.testing.feature.login.presentation

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null,
    val isButtonEnabled: Boolean = false
)