package com.ur4n0.castellari.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun ClientInputItem(
    labelText: String,
    placeholder: String,
    modifier: Modifier,
    value: String,
    keyboardType: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
            modifier = modifier,
            keyboardOptions = keyboardType
        )
    }
}