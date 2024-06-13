package br.com.fiap.fiapweb.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CrisisAlert
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalItems(icon: ImageVector, label: String, onClick: () -> Unit, selected: Boolean) {

    FilterChip(
        onClick = onClick,
        label = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        selected = selected,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
    )
}

@Composable
fun ModalFiltros(
    onDismissRequest: () -> Unit,
    onAplicarRequest: () -> Unit
) {
    val items = listOf("Urgentes", "Trabalho", "Estudos")
    val icons = listOf(Icons.Default.CrisisAlert, Icons.Default.Work, Icons.Default.Book)
    val selectedStates = remember {
        mutableStateMapOf<String, Boolean>().apply {
            items.forEach { put(it, false) }
        }
    }

    Dialog(onDismissRequest = {
        onDismissRequest()
    }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .clickable(onClick = {})
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(
                            MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                        .width(300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Filtros",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Divider(color = Color.Gray, thickness = 1.dp)

                    items.forEachIndexed { index, label ->
                        ModalItems(
                            icon = icons[index],
                            label = label,
                            onClick = {
                                selectedStates[label] = !(selectedStates[label] ?: false)
                            },
                            selected = selectedStates[label] ?: false
                        )
                    }

                    Divider(color = Color.Gray, thickness = 1.dp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            onClick = { onDismissRequest() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Fechar")
                        }
                        TextButton(
                            onClick = onAplicarRequest
                        ) {
                            Text(text = "Aplicar")
                        }
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ModalPreviw() {
    ModalFiltros({}, {})
}
