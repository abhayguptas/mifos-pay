package org.mifospay.core.model.domain.pocket

import kotlinx.serialization.Serializable

@Serializable
data class LinkedAccount(
    val accountId: String,
    val accountName: String,
    val balance: Double,
    val currency: String,
    val accountType: String // e.g., "SAVINGS", "LOAN", "SHARE"
)
