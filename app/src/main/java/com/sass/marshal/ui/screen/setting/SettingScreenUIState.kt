package com.sass.marshal.ui.screen.setting

import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.setting.ThemeType

data class SettingScreenUIState(
    val currencyType: CurrencyType = CurrencyType.USDollar,
    val themeType: ThemeType = ThemeType.Default,
)
