package com.example.trackbillz.core.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class UserPreferences(
    val billNumber: String? = null,
    val previewBillDate: Long? = null,
    val previousBillNumberState: Boolean = false
)
