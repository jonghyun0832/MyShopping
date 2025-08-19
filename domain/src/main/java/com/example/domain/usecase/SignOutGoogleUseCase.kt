package com.example.domain.usecase

import com.example.domain.model.AccountInfo
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SignOutGoogleUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke() {
        accountRepository.signOutGoogle()
    }
}