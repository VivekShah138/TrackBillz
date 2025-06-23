package com.example.trackbillz.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val email:String? = null,
    val name: String? = null,
    val profileURI: String? = null,
    val giveName: String? = null,
    val familyName: String? = null,
    val pin: String? = null,
    val userId: String? = null
)
