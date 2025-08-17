package com.example.domain.usecase

import com.example.domain.model.Product
import com.example.domain.model.SearchKeyword
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchKeywordsUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke() : Flow<List<SearchKeyword>> {
        return searchRepository.getSearchKeywords()
    }
}