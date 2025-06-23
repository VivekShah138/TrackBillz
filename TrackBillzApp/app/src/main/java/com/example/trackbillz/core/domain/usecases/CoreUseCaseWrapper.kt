package com.example.trackbillz.core.domain.usecases

import com.example.trackbillz.core.domain.usecases.local.GetUserPin

data class CoreUseCaseWrapper(
    val getUserPin: GetUserPin
)
