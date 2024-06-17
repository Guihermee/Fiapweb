package br.com.fiap.fiapweb.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import br.com.fiap.fiapweb.Repository.EmailRepository


@Composable
fun EmailAdress(
    value: String,
    modifier: Modifier,
    keyboardType: KeyboardType,
    text: String,
    updateValue: (String)-> Unit,
    showDropDownIcon: Boolean = false,
    onDropDownClick: (() -> Unit)? = null

) {
//    val context = LocalContext.current
//    val emailRepository = EmailRepository(context)
    var showCcCcoFields by remember { mutableStateOf(false)  }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(12.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = updateValue,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            trailingIcon = {
                if (showDropDownIcon && onDropDownClick != null) {
                    IconButton(onClick = {
                        // Toggle the state to show/hide CC and CCO fields
                        showCcCcoFields = !showCcCcoFields
                        onDropDownClick()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowDropDown,
                            contentDescription = "Dropdown Arrow"
                        )
                    }
                }
            }
        )
    }
}


