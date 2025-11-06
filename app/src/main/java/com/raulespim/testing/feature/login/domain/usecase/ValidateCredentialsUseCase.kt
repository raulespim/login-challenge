package com.raulespim.testing.feature.login.domain.usecase

import com.raulespim.testing.core.common.utils.Result
import com.raulespim.testing.feature.login.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ValidateCredentialsUseCase(
    private val repository: LoginRepository
) {
    operator fun invoke(username: String, password: String): Flow<Result<Boolean>> = flow {

        try {

            when (val result = repository.isValidCredentials(username = username, password = password)) {
                is Result.Success -> {
                    emit(Result.Success(result.data))
                }
                is Result.Error -> {
                    emit(Result.Success(false))
                }
            }

        } catch (e: Exception) {
            emit(Result.Error(e))
        }

    }.flowOn(Dispatchers.IO)
}