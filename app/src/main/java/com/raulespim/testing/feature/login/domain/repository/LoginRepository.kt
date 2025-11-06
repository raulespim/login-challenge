package com.raulespim.testing.feature.login.domain.repository

import com.raulespim.testing.core.common.utils.Result

interface LoginRepository {
    suspend fun isValidCredentials(username: String, password: String): Result<Boolean>
}