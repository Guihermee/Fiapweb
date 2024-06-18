package br.com.fiap.fiapweb.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.fiap.fiapweb.Repository.MarcadoresRepository
import br.com.fiap.fiapweb.model.Marcadores
import br.com.fiap.fiapweb.viewModel.TelaLerEmailViewModel


@Composable
fun ModalMarcadoresCriar(
    onDismissRequest: () -> Unit,
    telaLerEmailViewModel: TelaLerEmailViewModel
) {

    val context = LocalContext.current
    val marcadoresRepository = MarcadoresRepository(context)
    val bookmarkNomeField by telaLerEmailViewModel.bookmarkNomeField.observeAsState(initial = "")

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector = Icons.Outlined.Bookmark, contentDescription = "Bookmark Icon")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Criar Marcador", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = bookmarkNomeField,
                    onValueChange = { telaLerEmailViewModel.onBookmarkNomeFieldChange(it) },
                    label = { Text(text = "Nome do marcador") },
                    placeholder = { Text(text = "Ex: Estudos") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Cancelar")
                    }
                    TextButton(
                        onClick = {
                            val marcador = Marcadores(
                                id = 0,
                                nome = bookmarkNomeField
                            )
                            marcadoresRepository.salvar(marcador)
                            telaLerEmailViewModel.onVerificaListaMarcadoresChange(false)
                            telaLerEmailViewModel.onListaDropdownChange(marcadoresRepository.listar())

                            telaLerEmailViewModel.onModalCriarStateChange(false)
                            telaLerEmailViewModel.onModalAdicionarStateChange(true)
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Confirmar")
                    }
                }
            }
        }
    }
}