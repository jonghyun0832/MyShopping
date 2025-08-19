package com.example.domain.repository

import com.example.domain.model.AccountInfo
import kotlinx.coroutines.flow.StateFlow

interface AccountRepository {
    fun getAccountInfo() : StateFlow<AccountInfo?>

    suspend fun signIn(accountInfo: AccountInfo)

    suspend fun signOut()
}