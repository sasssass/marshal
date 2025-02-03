package com.sass.marshal.screen.listOfCrypto

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.sass.data.di.NetworkModule
import com.sass.marshal.HiltTestActivity
import com.sass.marshal.ui.MainCompose
import com.sass.marshal.ui.screen.listOfCrypto.ListOfCryptoScreenGraphNode
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
)
@HiltAndroidTest
class ListOfCryptoScreenTest {
    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testUI() =
        runTest {
            composeTestRule.setContent {
                MainCompose(ListOfCryptoScreenGraphNode.route)
            }
            advanceUntilIdle()
            composeTestRule.onNodeWithText("Bitcoin").assertExists()

            composeTestRule.onNodeWithText("Bitcoin").performClick()

            advanceUntilIdle()

            composeTestRule.onNodeWithText("x.com").assertExists()
        }
}
