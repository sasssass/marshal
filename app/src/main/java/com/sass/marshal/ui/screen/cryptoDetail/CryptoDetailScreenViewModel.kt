package com.sass.marshal.ui.screen.cryptoDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.utils.Response
import com.sass.domain.usecase.crypto.GetCryptoUseCase
import com.sass.domain.usecase.setting.GetSavedCurrencyTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoDetailScreenViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val getCryptoUseCase: GetCryptoUseCase,
        private val getSavedCurrencyTypeUseCase: GetSavedCurrencyTypeUseCase,
    ) : ViewModel() {
        private val currencyId: String = checkNotNull(savedStateHandle[CryptoDetailScreenGraphNode.crypto_id])

        private val _uiState: MutableStateFlow<CryptoDetailScreenUIState> = MutableStateFlow(CryptoDetailScreenUIState())
        val uiState = _uiState.asStateFlow()

        private lateinit var currencyType: CurrencyType

        init {
            viewModelScope.launch {
                getSavedCurrencyTypeUseCase.invoke(Unit).collect {
                    currencyType = it
                    getCryptoInfo()
                }
            }
        }

        private fun getCryptoInfo() {
            viewModelScope.launch {
                getCryptoUseCase
                    .invoke(
                        GetCryptoUseCase.Param(
                            currencyType,
                            currencyId,
                        ),
                    ).collect { response ->
                        when (response) {
                            is Response.Data -> {
                                _uiState.update {
                                    it.copy(
                                        loading = false,
                                        error = null,
                                        cryptoCurrency = response.data,
                                    )
                                }
                            }
                            is Response.Error -> {
                                _uiState.update {
                                    it.copy(
                                        loading = false,
                                        error = response.error.message,
                                    )
                                }
                            }
                            is Response.IDLE -> {}
                            is Response.Loading -> {
                                _uiState.update {
                                    it.copy(
                                        loading = true,
                                        error = null,
                                    )
                                }
                            }
                        }
                    }
            }
        }
    }
