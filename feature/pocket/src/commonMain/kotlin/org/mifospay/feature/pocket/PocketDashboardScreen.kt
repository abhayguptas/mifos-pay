package org.mifospay.feature.pocket

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.mifospay.core.model.domain.pocket.LinkedAccount

@Composable
fun PocketDashboardScreen(
    viewModel: PocketViewModel
) {
    val viewState by viewModel.pocketState.collectAsState()

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(title = { Text("My Pockets") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val state = viewState) {
                is PocketState.ViewState.Loading -> {
                    CircularProgressIndicator()
                }
                is PocketState.ViewState.Error -> {
                    Text(text = "Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                }
                is PocketState.ViewState.Content -> {
                    if (state.pockets.isEmpty()) {
                        Text("No pockets found. Link an account to get started.")
                    } else {
                        val pocket = state.pockets.first() // Assuming single pocket for POC
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                Column(
                                    modifier = Modifier.padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("Total Balance", style = MaterialTheme.typography.titleMedium)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        "${pocket.totalBalance} ${pocket.currency}", 
                                        style = MaterialTheme.typography.headlineLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Linked Accounts", style = MaterialTheme.typography.titleLarge)
                                TextButton(onClick = { viewModel.setEventAction(PocketAction.ManagePocketClicked) }) {
                                    Text("Manage")
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            LazyColumn {
                                items(pocket.linkedAccounts) { account ->
                                    LinkedAccountItem(account)
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = { viewModel.setEventAction(PocketAction.LinkAccountClicked) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Link New Account")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LinkedAccountItem(account: LinkedAccount) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(account.accountName, fontWeight = FontWeight.SemiBold)
                Text(account.accountType, style = MaterialTheme.typography.bodySmall)
            }
            Text(
                "${account.balance} ${account.currency}",
                fontWeight = FontWeight.Bold
            )
        }
    }
}
