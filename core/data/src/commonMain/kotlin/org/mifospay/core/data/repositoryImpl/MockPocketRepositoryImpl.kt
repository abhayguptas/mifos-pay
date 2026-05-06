package org.mifospay.core.data.repositoryImpl

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.mifospay.core.common.DataState
import org.mifospay.core.data.repository.PocketRepository
import org.mifospay.core.model.domain.pocket.LinkAccountRequest
import org.mifospay.core.model.domain.pocket.LinkedAccount
import org.mifospay.core.model.domain.pocket.Pocket

class MockPocketRepositoryImpl : PocketRepository {
    
    // Hardcoded mock data to unblock UI development
    private val mockAccounts = mutableListOf(
        LinkedAccount(
            accountId = "ACC_001",
            accountName = "Main Savings Account",
            balance = 1250.00,
            currency = "USD",
            accountType = "SAVINGS"
        ),
        LinkedAccount(
            accountId = "ACC_002",
            accountName = "Auto Loan",
            balance = -5500.00,
            currency = "USD",
            accountType = "LOAN"
        )
    )

    private val mockPocket = Pocket(
        pocketId = "PKT_999",
        totalBalance = -4250.00,
        currency = "USD",
        linkedAccounts = mockAccounts
    )

    override fun getPockets(): Flow<DataState<List<Pocket>>> = flow {
        emit(DataState.Loading)
        delay(1000) // Simulate network delay
        emit(DataState.Success(listOf(mockPocket)))
    }

    override suspend fun linkAccount(request: LinkAccountRequest): DataState<Pocket> {
        delay(800) // Simulate network delay
        val newAccount = LinkedAccount(
            accountId = request.accountId,
            accountName = "Newly Linked ${request.accountType}",
            balance = 500.0,
            currency = "USD",
            accountType = request.accountType
        )
        mockAccounts.add(newAccount)
        
        val updatedPocket = mockPocket.copy(
            totalBalance = mockPocket.totalBalance + newAccount.balance,
            linkedAccounts = mockAccounts.toList()
        )
        return DataState.Success(updatedPocket)
    }

    override suspend fun delinkAccount(request: LinkAccountRequest): DataState<Pocket> {
        delay(800) // Simulate network delay
        val accountToRemove = mockAccounts.find { it.accountId == request.accountId }
        if (accountToRemove != null) {
            mockAccounts.remove(accountToRemove)
            val updatedPocket = mockPocket.copy(
                totalBalance = mockPocket.totalBalance - accountToRemove.balance,
                linkedAccounts = mockAccounts.toList()
            )
            return DataState.Success(updatedPocket)
        }
        return DataState.Error(Exception("Account not found in pocket"))
    }
}
