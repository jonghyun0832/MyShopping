package com.example.data.repository

import com.example.data.datasource.PreferenceDataSource
import com.example.data.db.dao.BasketDao
import com.example.data.db.dao.LikeDao
import com.example.domain.model.AccountInfo
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource,
    private val basketDao: BasketDao,
    private val likeDao: LikeDao
) : AccountRepository {
    private val accountInfoFlow = MutableStateFlow(preferenceDataSource.getAccountInfo())

    override fun getAccountInfo(): StateFlow<AccountInfo?> {
        return accountInfoFlow
    }

    override suspend fun signIn(accountInfo: AccountInfo) {
        preferenceDataSource.putAccountInfo(accountInfo)
        accountInfoFlow.emit(accountInfo)
    }

    override suspend fun signOut() {
        preferenceDataSource.removeAccountInfo()
        accountInfoFlow.emit(null)
        basketDao.deleteAll()
        likeDao.deleteAll()
    }
}