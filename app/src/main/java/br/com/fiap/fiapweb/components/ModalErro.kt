package br.com.fiap.fiapweb.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ModalErro(
    mensagemErro: String,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Erro ao Enviar E-mail") },
        text = { Text(text = mensagemErro) },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("OK")
            }
        }
    )
}
