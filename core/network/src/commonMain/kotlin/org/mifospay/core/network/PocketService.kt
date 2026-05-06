package org.mifospay.core.network

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import org.mifospay.core.model.domain.pocket.LinkAccountRequest
import org.mifospay.core.model.domain.pocket.Pocket

interface PocketService {

    @GET("self/pockets")
    suspend fun getPockets(): List<Pocket>

    @POST("self/pockets?command=linkAccounts")
    suspend fun linkAccount(@Body request: LinkAccountRequest): Pocket

    @POST("self/pockets?command=delinkAccounts")
    suspend fun delinkAccount(@Body request: LinkAccountRequest): Pocket
}
