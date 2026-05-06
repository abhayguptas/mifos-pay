package org.mifospay.core.model.domain.pocket

import kotlinx.serialization.Serializable

@Serializable
data class LinkAccountRequest(
    val accountId: String,
    val accountType: String
)
