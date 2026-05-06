package org.mifospay.core.model.domain.pocket

import kotlinx.serialization.Serializable

@Serializable
data class Pocket(
    val pocketId: String,
    val totalBalance: Double,
    val currency: String,
    val linkedAccounts: List<LinkedAccount>
)
