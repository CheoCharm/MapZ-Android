package com.cheocharm.presentation.model

data class Result<T>(val isSuccessful: Boolean, val message: String? = null, val data: T? = null)
