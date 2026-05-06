# Pocket Feature POC: What & Why

This document outlines the implementation approach and technical decisions behind the Pocket Feature (Linked Accounts) Proof of Concept (Draft PR).

## 1. What Was Implemented

Instead of building only a UI prototype or a standalone API layer, this POC implements a complete vertical slice of the feature across the KMP architecture.

### Domain Models (`core/model`)
Created the following models with Kotlin serialization support:

- `Pocket.kt`
- `LinkedAccount.kt`

Both models are annotated with `@Serializable` to ensure consistent serialization across platforms.

### Network Contract (`core/network`)
Defined `PocketService.kt` using Ktorfit to map the required API endpoints:

- `GET` endpoints for fetching pocket data
- `POST` endpoints for linked account actions

This establishes a clear network contract for future backend integration.

### Data Layer (`core/data`)
Implemented:

- `PocketRepository.kt` as the repository contract
- `MockPocketRepositoryImpl.kt` as the initial implementation

The mock repository uses:

- Kotlin Coroutines
- `Flow`
- `delay()`

to simulate asynchronous network behavior while returning synthetic data.

### Presentation Layer (`feature/pocket`)
Built the feature UI using:

- `PocketViewModel.kt`
- `StateFlow` for reactive state management
- Material 3 Compose components

The UI is exposed through:

- `PocketDashboardScreen.kt`

---

## 2. Implementation Decisions

### Vertical Slice Implementation

The feature was implemented across all architectural layers instead of isolating the UI or data layer.

This approach validates:

- module boundaries
- dependency flow
- data transformation across layers
- consistency with the existing KMP architecture

It also provides a working foundation that can be integrated directly into the codebase.

### Mock Repository

A mock repository was introduced to enable feature development independently of backend availability.

This provides several advantages:

- UI development can continue without waiting for backend deployment
- state handling can be tested early
- API contracts can be validated before integration

When backend APIs are available, the mock implementation can be replaced with a network-backed repository through dependency injection without requiring changes in the ViewModel or UI layers.

### StateFlow for State Management

`StateFlow` was selected as the primary state management mechanism.

Benefits include:

- platform-independent reactive state handling
- seamless integration with Kotlin Multiplatform
- predictable UI updates
- lifecycle-aware state observation in Compose

The UI state is modeled using a sealed `PocketUiState`:

- `Loading`
- `Success`
- `Error`

This ensures that all UI states are explicitly handled and simplifies state-driven rendering.

---

## Summary

This POC establishes a production-ready foundation for the Pocket Feature by implementing:

- clear separation of concerns
- modular architecture
- reactive state management
- backend-independent feature development

The current implementation can be extended by replacing the mock repository with a network-backed implementation while keeping the presentation layer unchanged.
