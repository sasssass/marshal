package com.sass.marshal.ui.screen.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.setting.ThemeType
import sass.compose.annotation.NavigationNode

@NavigationNode(route = "setting")
@Composable
fun SettingScreen(viewModel: SettingScreenViewModel) {
    SettingScreen(
        viewModel.uiState.collectAsState().value,
        viewModel::switchCurrencyType,
        viewModel::changeThemeState
    )
}

@Composable
private fun SettingScreen(
    uiState: SettingScreenUIState,
    changeCurrencyType: (CurrencyType) -> Unit,
    changeThemeType: (ThemeType) -> Unit,
) {
    val currencyTypes = CurrencyType.entries
    val themeTypes = ThemeType.entries

    Column(
        modifier = Modifier.fillMaxSize().padding(
            start = 4.dp, end = 4.dp, top = 16.dp, bottom = 8.dp
        ),
    ) {

        Text(text = "Currency Type", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(start = 8.dp))

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(8.dp),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
        ) {
            currencyTypes.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.testTag(it.name).fillMaxWidth().clickable {
                        changeCurrencyType(it)
                    }
                ) {
                    RadioButton(
                        modifier = Modifier.testTag(it.name+"radio"),
                        selected = (it == uiState.currencyType),
                        onClick = { changeCurrencyType(it) },
                    )
                    Text(text = it.realName, style = MaterialTheme.typography.labelLarge)
                }

                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Text(text = "Theme mode", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(start = 8.dp))

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(8.dp),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
        ) {
            themeTypes.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.testTag(it.name).fillMaxWidth().clickable {
                        changeThemeType(it)
                    }
                ) {
                    RadioButton(
                        modifier = Modifier.testTag(it.name+"radio"),
                        selected = (it == uiState.themeType),
                        onClick = { changeThemeType(it) },
                    )
                    Text(text = it.name, style = MaterialTheme.typography.labelLarge)
                }

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Preview
@Composable
fun PreviewSettingScreen() {
    SettingScreen(
        SettingScreenUIState(),
        changeCurrencyType = {},
        changeThemeType = {},
    )
}
