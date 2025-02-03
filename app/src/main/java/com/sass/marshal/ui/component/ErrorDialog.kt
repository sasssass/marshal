package com.sass.marshal.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.sass.marshal.R

@Composable
fun ErrorDialog(
    error: String,
    onDismiss:() -> Unit
) {
    AlertDialog(
        icon = {
            Icon(painter = painterResource(id = R.drawable.ic_error), contentDescription = "Error")
        },
        title = {
            "Error"
        },
        text = {
            Text(text = error)
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Ok")
            }
        }
    )
}
