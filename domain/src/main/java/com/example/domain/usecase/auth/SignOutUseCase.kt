package com.example.domain.usecase.auth

import com.example.domain.repository.AccountRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke() {
        accountRepository.signOut()
    }
}