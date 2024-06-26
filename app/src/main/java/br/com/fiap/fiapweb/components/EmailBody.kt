package br.com.fiap.fiapweb.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun EmailBody(
    value: String,
    keyboardType: KeyboardType,
    updateValue: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = updateValue,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier.fillMaxSize(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            )
        )
    }
}
