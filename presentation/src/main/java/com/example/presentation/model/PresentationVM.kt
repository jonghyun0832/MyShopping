package com.example.presentation.model

import com.example.domain.model.BaseModel

sealed class PresentationVM<T : BaseModel>(val model: T) {
}