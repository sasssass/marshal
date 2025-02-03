package com.sass.marshal.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.setting.ThemeType
import com.sass.domain.usecase.setting.ChangeSavedCurrencyTypeUseCase
import com.sass.domain.usecase.setting.ChangeThemeStateUseCase
import com.sass.domain.usecase.setting.GetSavedCurrencyTypeUseCase
import com.sass.domain.usecase.setting.GetThemeStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingScreenViewModel
    @Inject
    constructor(
        private val getSavedCurrencyTypeUseCase: GetSavedCurrencyTypeUseCase,
        private val changeSavedCurrencyTypeUseCase: ChangeSavedCurrencyTypeUseCase,
        private val getThemeStateUseCase: GetThemeStateUseCase,
        private val changeThemeStateUseCase: ChangeThemeStateUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SettingScreenUIState())
        val uiState = _uiState.asStateFlow()

        init {
            viewModelScope.launch {
                getSavedCurrencyTypeUseCase.invoke(Unit).collect { response ->
                    _uiState.update {
                        it.copy(
                            currencyType = response,
                        )
                    }
                }
            }

            viewModelScope.launch {
                getThemeStateUseCase.invoke(Unit).collect { response ->
                    _uiState.update {
                        it.copy(
                            themeType = response,
                        )
                    }
                }
            }
        }

        fun switchCurrencyType(type: CurrencyType) {
            viewModelScope.launch {
                changeSavedCurrencyTypeUseCase.invoke(type)
            }
        }

        fun changeThemeState(type: ThemeType) {
            viewModelScope.launch {
                changeThemeStateUseCase.invoke(type)
            }
        }
    }
