package com.sass.marshal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sass.domain.model.setting.ThemeType
import com.sass.domain.usecase.setting.GetThemeStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
    @Inject
    constructor(
        private val getThemeStateUseCase: GetThemeStateUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ThemeType.Default)
        val uiState = _uiState.asStateFlow()

        init {
            viewModelScope.launch {
                getThemeStateUseCase.invoke(Unit).collect {
                    _uiState.value = it
                }
            }
        }
    }
