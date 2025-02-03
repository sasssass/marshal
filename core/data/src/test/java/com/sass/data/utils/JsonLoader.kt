package com.sass.data.utils

import java.io.BufferedReader
import java.io.InputStreamReader

fun readJsonFile(fileName: String): String {
    val inputStream = Thread.currentThread().contextClassLoader?.getResourceAsStream(fileName)
        ?: throw IllegalArgumentException("File not found: $fileName")
    return BufferedReader(InputStreamReader(inputStream)).use { it.readText() }
}