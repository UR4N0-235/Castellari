package com.ur4n0.castellari.viewmodel

import androidx.compose.foundation.layout.Row
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun InputItem(labelText: String, modifier: Modifier) {
    Row {
        val text = remember { mutableStateOf("") }

        OutlinedTextField(
            value = text.value,
            onValueChange = {
                text.value = it
            }, label = {
               Text(text = labelText)
            },
            modifier = modifier
        )

    }
}