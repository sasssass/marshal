package com.sass.marshal.ui.screen.cryptoDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sass.domain.model.currency.CryptoCurrency
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.currency.Links
import com.sass.marshal.R
import com.sass.marshal.ui.component.ErrorDialog
import com.sass.marshal.ui.util.openUrl
import com.valentinilk.shimmer.shimmer
import sass.compose.annotation.NavigationNode

@NavigationNode(
    route = "crypto_detail",
    args = ["crypto_id"],
)
@Composable
fun CryptoDetailScreen(
    navController: NavController,
    viewModel: CryptoDetailScreenViewModel,
) {
    CryptoDetailScreen(
        viewModel.uiState.collectAsState().value,
        onBack = {
            navController.popBackStack()
        },
    )
}

@Composable
private fun CryptoDetailScreen(
    uiState: CryptoDetailScreenUIState,
    onBack: () -> Unit = {},
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        if (uiState.error != null) {
            ErrorDialog(error = uiState.error) {
                onBack()
            }
        }

        if (!uiState.loading) {
            uiState.cryptoCurrency?.let {
                val crypto = uiState.cryptoCurrency

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SubcomposeAsyncImage(
                        model =
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(uiState.cryptoCurrency.icon)
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
                                .width(48.dp)
                                .height(48.dp)
                                .padding(2.dp),
                        contentScale = ContentScale.Crop,
                    )

                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        modifier =
                            Modifier
                                .padding(8.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(text = crypto.name)

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(text = "${crypto.value} ${crypto.valueType.sign}")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                crypto.run {
                    PairInfo(pair = Pair("Coin code:", code))
                    PairInfo(pair = Pair("Market Cap:", marketCap.toString()))
                    PairInfo(pair = Pair("Total supply:", totalSupply.toString()))
                    links?.let {
                        it.twitter?.let {
                            Link(pair = Pair(R.drawable.icon_x, it))
                        }
                        it.github?.let {
                            Link(pair = Pair(R.drawable.ic_github, it))
                        }
                        it.reddit?.let {
                            Link(pair = Pair(R.drawable.ic_reddit, it))
                        }
                        it.facebook?.let {
                            Link(pair = Pair(R.drawable.ic_facebook, it))
                        }
                        it.website?.let {
                            Link(pair = Pair(R.drawable.ic_net, it))
                        }
                    }
                }
            }
        } else {
            Shimmer()
        }
    }
}

@Composable
fun PairInfo(pair: Pair<String, String>) {
    Column {
        Spacer(modifier = Modifier.height(4.dp))

        Row {
            Text(text = pair.first)
            Spacer(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
            )
            Text(text = pair.second)
        }

        Spacer(modifier = Modifier.height(4.dp))

        HorizontalDivider()
    }
}

@Composable
fun Link(pair: Pair<Int, String>) {
    Column {
        Spacer(modifier = Modifier.height(4.dp))

        val context = LocalContext.current

        Row(
            modifier =
                Modifier.clickable {
                    openUrl(pair.second, context)
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = pair.first),
                contentDescription = pair.second,
                modifier =
                    Modifier
                        .width(24.dp)
                        .height(24.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = pair.second)
        }

        Spacer(modifier = Modifier.height(4.dp))

        HorizontalDivider()
    }
}

@Composable
private fun Shimmer() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .padding(4.dp)
                    .shimmer()
                    .background(MaterialTheme.colorScheme.secondary),
        )

        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                text = "                   ",
                modifier =
                    Modifier
                        .shimmer()
                        .background(MaterialTheme.colorScheme.secondary),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "                   ",
                modifier =
                    Modifier
                        .shimmer()
                        .background(MaterialTheme.colorScheme.secondary),
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    for (i in 0..8) {
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "                                      ",
            modifier =
                Modifier
                    .shimmer()
                    .background(MaterialTheme.colorScheme.secondary),
        )

        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Preview
@Composable
fun PreviewCryptoDetailScreen() {
    CryptoDetailScreen(
        uiState =
            CryptoDetailScreenUIState(
                cryptoCurrency =
                    CryptoCurrency(
                        name = "Bitcoin",
                        id = 1,
                        code = "BTC",
                        icon = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
                        value = 10.0,
                        marketCap = 10.0,
                        totalSupply = 10.0,
                        valueType = CurrencyType.USDollar,
                        links =
                            Links(
                                twitter = "test",
                                github = "test",
                                facebook = "test",
                                reddit = "test",
                                website = "test",
                            ),
                    ),
            ),
    )
}
