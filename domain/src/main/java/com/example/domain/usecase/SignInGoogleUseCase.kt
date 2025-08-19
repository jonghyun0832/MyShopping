package com.example.domain.usecase

import com.example.domain.model.AccountInfo
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SignInGoogleUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(accountInfo: AccountInfo) {
        accountRepository.signInGoogle(accountInfo)
    }
}