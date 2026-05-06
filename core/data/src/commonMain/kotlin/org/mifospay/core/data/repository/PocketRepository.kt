package org.mifospay.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.mifospay.core.common.DataState
import org.mifospay.core.model.domain.pocket.LinkAccountRequest
import org.mifospay.core.model.domain.pocket.Pocket

interface PocketRepository {
    fun getPockets(): Flow<DataState<List<Pocket>>>
    suspend fun linkAccount(request: LinkAccountRequest): DataState<Pocket>
    suspend fun delinkAccount(request: LinkAccountRequest): DataState<Pocket>
}
