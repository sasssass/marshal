package com.sass.marshal.ui.screen.listOfCrypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sass.domain.model.currency.CryptoCurrency
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.utils.Response
import com.sass.domain.usecase.crypto.GetListOfCryptoUseCase
import com.sass.domain.usecase.setting.GetSavedCurrencyTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfCryptoScreenViewModel
    @Inject
    constructor(
        private val getListOfCryptoUseCase: GetListOfCryptoUseCase,
        private val getSavedCurrencyTypeUseCase: GetSavedCurrencyTypeUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ListOfCryptoScreenUIState())
        val uiState = _uiState.asStateFlow()

        private var allCurrencies: List<CryptoCurrency> = emptyList()

        init {
            viewModelScope.launch {
                getSavedCurrencyTypeUseCase.invoke(Unit).collect {
                    getListOfCrypto(it)
                }
            }
        }

        private fun getListOfCrypto(currencyType: CurrencyType) {
            viewModelScope.launch {
                getListOfCryptoUseCase.invoke(currencyType).collect { response ->
                    when (response) {
                        is Response.Loading -> {
                            _uiState.update {
                                it.copy(
                                    loading = true,
                                    error = null,
                                )
                            }
                        }

                        is Response.Data -> {
                            allCurrencies = response.data
                            _uiState.update {
                                it.copy(
                                    loading = false,
                                    error = null,
                                    cryptoCurrencyList = response.data,
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
                        is Response.IDLE -> {
                            // do nothing
                        }
                    }
                }
            }
        }

        fun search(input: String) {
            val newCryptoCurrencyList =
                allCurrencies.filter {
                    it.name.lowercase().contains(input.lowercase()) || it.code.lowercase().contains(input.lowercase())
                }
            _uiState.update {
                it.copy(
                    cryptoCurrencyList = newCryptoCurrencyList,
                )
            }
        }
    }
