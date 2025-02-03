package com.sass.marshal.ui.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openUrl(
    url: String,
    context: Context,
) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}
