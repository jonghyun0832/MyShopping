package com.example.domain.usecase

import com.example.domain.model.AccountInfo
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAccountInfoUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(): StateFlow<AccountInfo?> {
        return accountRepository.getAccountInfo()
    }
}