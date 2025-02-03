package com.sass.marshal.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sass.marshal.R

@Composable
fun SearchField(onTextChange: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = {
            searchText = it
            onTextChange.invoke(it)
        },
        label = { Text("search...") },
        leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = "Search") },
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(4.dp),
    )
}
