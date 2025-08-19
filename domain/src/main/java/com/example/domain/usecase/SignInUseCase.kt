package com.example.domain.usecase

import com.example.domain.model.AccountInfo
import com.example.domain.repository.AccountRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(accountInfo: AccountInfo) {
        accountRepository.signIn(accountInfo)
    }
}