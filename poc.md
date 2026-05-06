# Pocket Feature POC: What & Why

This document explains the technical decisions and the implementation strategy behind the Pocket Feature (Linked Accounts) Proof of Concept (Draft PR).

## 1. What We Did
Rather than just building a disconnected UI mockup or a single API class, we built a **Vertical Architectural Slice** of the feature across all KMP layers:

* **Domain Models (`core/model`):** Created `Pocket.kt` and `LinkedAccount.kt`, fully annotated with `@Serializable`.
* **Network Contract (`core/network`):** Defined the `PocketService.kt` interface using Ktorfit to strictly map the GET and POST endpoints.
* **Data Layer (`core/data`):** Created the `PocketRepository.kt` abstraction and a `MockPocketRepositoryImpl.kt` that utilizes Kotlin Coroutines (`delay`) and `Flow` to return synthetic data.
* **Presentation Layer (`feature/pocket`):** Built a reactive `PocketViewModel.kt` utilizing `StateFlow` and a KMP Material 3 Compose screen (`PocketDashboardScreen.kt`).

## 2. Why We Did It This Way

### Why a Vertical Slice instead of just UI?
To prove KMP proficiency. A senior engineer doesn't just write UI; they ensure the data flows cleanly from the network to the screen. By touching `core/model`, `core/network`, `core/data`, and `feature`, we prove that we understand the project's strict modular boundaries and dependency injection patterns.

### Why a Mock Repository?
**Unblocking UI Development.** In early-stage feature development, waiting for a fully deployed backend or struggling with local server setups wastes time. 
* By creating `MockPocketRepositoryImpl.kt`, the UI can be built, styled, and state-tested immediately. 
* It explicitly defines the contract. When the real API is ready, we simply swap `MockPocketRepositoryImpl` for `NetworkPocketRepositoryImpl` in the DI graph. The UI and ViewModel code will not change by a single line.

### Why StateFlow over standard Callbacks?
**Reactivity & KMP Compatibility.** `StateFlow` is a pure Kotlin construct, making it perfectly suited for KMP. By combining `StateFlow` with a sealed `PocketUiState` class (Loading, Success, Error), we guarantee that our Compose UI exhaustively handles all possible states, eliminating entire classes of runtime UI bugs.

---
**Summary:** This POC is structured to drop straight into the codebase as a robust, scalable foundation, demonstrating clean code, separation of concerns, and pragmatic problem-solving.
