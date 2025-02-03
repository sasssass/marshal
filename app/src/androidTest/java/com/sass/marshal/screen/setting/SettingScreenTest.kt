package com.sass.marshal.screen.setting

import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.sass.data.di.DataStoreModule
import com.sass.data.di.NetworkModule
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.setting.ThemeType
import com.sass.marshal.HiltTestActivity
import com.sass.marshal.ui.MainCompose
import com.sass.marshal.ui.screen.setting.SettingScreenGraphNode
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@UninstallModules(
    NetworkModule::class,
    DataStoreModule::class,
)
@HiltAndroidTest
class SettingScreenTest {
    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testUI() =
        runTest {
            composeTestRule.setContent {
                MainCompose(SettingScreenGraphNode.route)
            }
            advanceUntilIdle()
            composeTestRule.onNodeWithTag(CurrencyType.SwedishKrona.name + "radio").assertIsNotSelected()

            composeTestRule.onNodeWithTag(CurrencyType.SwedishKrona.name).performClick()

            composeTestRule.onNodeWithTag(CurrencyType.SwedishKrona.name + "radio").assertIsSelected()

            composeTestRule.onNodeWithTag(ThemeType.Dark.name + "radio").assertIsNotSelected()

            composeTestRule.onNodeWithTag(ThemeType.Dark.name).performClick()

            composeTestRule.onNodeWithTag(ThemeType.Dark.name + "radio").assertIsSelected()
        }
}
