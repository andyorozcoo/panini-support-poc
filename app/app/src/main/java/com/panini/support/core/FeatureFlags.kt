package com.panini.support.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class FeatureFlagState(
    val enableTicketCreation: Boolean = true,
    val enablePriorityUpdates: Boolean = true
)

/**
 * Local in-app feature switches for internal PoC testing.
 * This keeps the scope simple while allowing testers to toggle behavior without code changes.
 */
object FeatureFlags {
    private val _state = MutableStateFlow(FeatureFlagState())
    val state: StateFlow<FeatureFlagState> = _state.asStateFlow()

    fun setTicketCreationEnabled(enabled: Boolean) {
        _state.update { current -> current.copy(enableTicketCreation = enabled) }
    }

    fun setPriorityUpdatesEnabled(enabled: Boolean) {
        _state.update { current -> current.copy(enablePriorityUpdates = enabled) }
    }
}