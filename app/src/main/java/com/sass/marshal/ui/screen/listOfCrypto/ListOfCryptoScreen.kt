package com.sass.marshal.ui.screen.listOfCrypto

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sass.domain.model.currency.CryptoCurrency
import com.sass.marshal.R
import com.sass.marshal.ui.component.ErrorDialog
import com.sass.marshal.ui.component.SearchField
import com.sass.marshal.ui.screen.cryptoDetail.CryptoDetailScreenGraphNode
import com.valentinilk.shimmer.shimmer
import sass.compose.annotation.NavigationNode

@Composable
@NavigationNode(
    route = "list_of_crypto",
)
fun ListOfCryptoScreen(
    navController: NavController,
    viewModel: ListOfCryptoScreenViewModel,
) {
    ListOfCryptoScreen(
        viewModel.uiState.collectAsState().value,
        search = {
            viewModel.search(it)
        },
        navigateToDetail = {
            navController.navigate(CryptoDetailScreenGraphNode.navigationRoute(it.toString()))
        },
        onBack = {
            navController.popBackStack()
        },
    )
}

@Composable
private fun ListOfCryptoScreen(
    uiState: ListOfCryptoScreenUIState,
    navigateToDetail: (Int) -> Unit = {},
    search: (String) -> Unit = {},
    onBack: () -> Unit = {},
) {
    Column {
        if (uiState.error != null) {
            ErrorDialog(error = uiState.error) {
                onBack()
            }
        } else {
            val lazyListState = rememberLazyListState()

            SearchField {
                search(it)
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
            ) {
                if (uiState.loading) {
                    items(20) {
                        Card(
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = RoundedCornerShape(8.dp),
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .padding(4.dp)
                                    .shimmer(),
                        ) {}
                    }
                } else {
                    items(
                        uiState.cryptoCurrencyList.size,
                        key = { uiState.cryptoCurrencyList[it].id },
                    ) {
                        Row(
                            modifier = Modifier.animateItem(),
                        ) {
                            Item(uiState.cryptoCurrencyList[it]) {
                                navigateToDetail(it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Item(
    item: CryptoCurrency,
    navigateToDetail: (Int) -> Unit = {},
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        modifier =
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(4.dp)
                .clickable {
                    navigateToDetail(item.id)
                },
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            SubcomposeAsyncImage(
                model =
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(item.icon)
                        .crossfade(true)
                        .build(),
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_money),
                        contentDescription = null,
                        modifier =
                            Modifier
                                .width(24.dp)
                                .height(24.dp)
                                .padding(2.dp),
                    )
                },
                contentDescription = null,
                modifier =
                    Modifier
                        .width(24.dp)
                        .height(24.dp)
                        .padding(2.dp),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(text = item.name)

            Spacer(modifier = Modifier.weight(1f).fillMaxWidth())

            Text(text = "${item.value} ${item.valueType.sign}")
        }
    }
}
