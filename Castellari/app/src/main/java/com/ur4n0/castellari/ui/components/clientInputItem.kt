package com.ur4n0.castellari.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ClientInputItem(
    labelText: String,
    placeholder: String,
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(text = labelText)
            },
            placeholder = {
                Text(text = placeholder)
            },
            modifier = modifier
        )
    }
}