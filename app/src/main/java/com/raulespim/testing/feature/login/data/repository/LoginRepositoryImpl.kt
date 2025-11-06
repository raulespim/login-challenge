package com.raulespim.testing.feature.login.data.repository

import com.raulespim.testing.core.common.utils.Result
import com.raulespim.testing.feature.login.domain.repository.LoginRepository

class LoginRepositoryImpl() : LoginRepository {

    override suspend fun isValidCredentials(username: String, password: String): Result<Boolean> {

        return try {
            val usernameTest = "raul.espim@example.com"
            val passwordTest = "123456"

            if (username == usernameTest && password == passwordTest) {
                Result.Success(true)
            } else {
                Result.Success(false)
            }

        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}