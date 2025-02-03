package com.sass.marshal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.sass.domain.model.setting.ThemeType
import com.sass.marshal.ui.MainCompose
import com.sass.marshal.ui.theme.MarshalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            viewModel.uiState.collectAsState().value
            MarshalTheme(
                darkTheme = viewModel.uiState.collectAsState().value == ThemeType.Dark,
            ) {
                MainCompose()
            }
        }
    }
}
