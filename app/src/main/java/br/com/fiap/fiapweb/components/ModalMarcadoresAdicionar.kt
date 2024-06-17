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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
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
import br.com.fiap.fiapweb.Repository.EmailRepository
import br.com.fiap.fiapweb.Repository.MarcadoresRepository
import br.com.fiap.fiapweb.model.Email
import br.com.fiap.fiapweb.viewModel.TelaLerEmailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalMarcadoresAdicionar(
    onDismissRequest: () -> Unit,
    telaLerEmailViewModel: TelaLerEmailViewModel,
    email: Email,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    val marcadoresRepository = MarcadoresRepository(context)
    val usuarioRepository = EmailRepository(context)
    val listaMarcadoress =
        marcadoresRepository.listar().toMutableList().filterNot { it.nome == "Generico" }
    val listaMarcadores by telaLerEmailViewModel.listaDropdown.observeAsState(initial = listaMarcadoress)
    val dropdownState by telaLerEmailViewModel.dropdownState.observeAsState(initial = false)
    val selectedMarcador by telaLerEmailViewModel.selectedMarcador.observeAsState(initial = listaMarcadores[0])

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

                Text(text = "Adicionar Marcador", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                ExposedDropdownMenuBox(
                    expanded = dropdownState,
                    onExpandedChange = { telaLerEmailViewModel.onDropdownStateChange(!dropdownState) }
                ) {
                    TextField(
                        modifier = Modifier.menuAnchor(),
                        value = selectedMarcador.nome,
                        onValueChange = {},
                        readOnly = true,
                    )
                    ExposedDropdownMenu(
                        expanded = dropdownState,
                        onDismissRequest = { telaLerEmailViewModel.onDropdownStateChange(false) }) {
                        listaMarcadores.forEachIndexed { index, marcadores ->
                            DropdownMenuItem(text = { Text(text = marcadores.nome) }, onClick = {
                                telaLerEmailViewModel.onSelectedMarcadorChange(listaMarcadores[index])
                                telaLerEmailViewModel.onDropdownStateChange(false)
                            })
                        }
                    }
                }
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
                            val idMarcadorNovo = selectedMarcador.id
                            val emailAlterado = email.copy(marcadorId = idMarcadorNovo)
                            usuarioRepository.atualizar(emailAlterado)

                            telaLerEmailViewModel.onModalAdicionarStateChange(false)

                            scope.launch {
                                snackbarHostState.showSnackbar("Marcador adicionado no Email com sucesso!")
                            }
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Adicionar Marcador")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Deseja Criar mais Marcadores?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { telaLerEmailViewModel.onModalCriarStateChange(true) }) {
                    Text(text = "Criar Marcador")
                }
            }
        }
    }
}