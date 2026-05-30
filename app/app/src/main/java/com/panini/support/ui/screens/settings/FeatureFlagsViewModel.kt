package com.panini.support.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panini.support.core.FeatureFlags
import com.panini.support.core.FeatureFlagState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FeatureFlagsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FeatureFlagState())
    val uiState: StateFlow<FeatureFlagState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            FeatureFlags.state.collect { flags -> _uiState.value = flags }
        }
    }

    fun setTicketCreationEnabled(enabled: Boolean) {
        FeatureFlags.setTicketCreationEnabled(enabled)
    }

    fun setPriorityUpdatesEnabled(enabled: Boolean) {
        FeatureFlags.setPriorityUpdatesEnabled(enabled)
    }
}