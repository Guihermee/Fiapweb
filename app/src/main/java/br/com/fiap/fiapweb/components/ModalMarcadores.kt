package br.com.fiap.fiapweb.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.fiap.fiapweb.Repository.MarcadoresRepository
import br.com.fiap.fiapweb.model.Marcadores
import br.com.fiap.fiapweb.viewModel.TelaLerEmailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalMarcadores(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    telaLerEmailViewModel: TelaLerEmailViewModel
) {

    val context = LocalContext.current
    val marcadoresRepository = MarcadoresRepository(context)
    val listaMarcadoress = marcadoresRepository.listar().toMutableList()
    val listaMarcadores by telaLerEmailViewModel.listaDropdown.observeAsState(initial = listaMarcadoress)
    val novaListaDeMarcadores = listaMarcadores.filterNot { it.nome == "Generico" }

    val bookmarkNomeField by telaLerEmailViewModel.bookmarkNomeField.observeAsState(initial = "")
    val bookmarkCorField by telaLerEmailViewModel.bookmarkCorField.observeAsState(initial = "")
    val listaMarcadoresVazia by telaLerEmailViewModel.verificaListaMarcadores.observeAsState(initial = novaListaDeMarcadores.isEmpty())
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
                if (listaMarcadoresVazia) { // Não existe nenhum Marcador
                    Text(text = "Criar Marcador", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Parece que você não tem nenhum Marcador, preencha a baixo para criar e começar a usar!",
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = bookmarkNomeField,
                        onValueChange = { telaLerEmailViewModel.onBookmarkNomeFieldChange(it) },
                        label = { Text(text = "Nome do marcador") },
                        placeholder = { Text(text = "Ex: Estudos") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = bookmarkCorField,
                        onValueChange = { telaLerEmailViewModel.onBookmarkCorFieldChange(it) },
                        label = { Text(text = "Cor do marcador") },
                        placeholder = { Text(text = "Ex: Preto") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    Text(text = "Selecionar Marcador", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                        ExposedDropdownMenu(expanded = dropdownState, onDismissRequest = { telaLerEmailViewModel.onDropdownStateChange(false) }) {
                            listaMarcadores.forEachIndexed { index, marcadores ->
                                DropdownMenuItem(text = { Text(text = marcadores.nome) }, onClick = {
                                    telaLerEmailViewModel.onSelectedMarcadorChange(listaMarcadores[index])
                                    telaLerEmailViewModel.onDropdownStateChange(false)
                                })
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
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

                    // Se exisitr um Marcador esse botão de Criar um ficará habilitado para o usuário
                    if (!listaMarcadoresVazia ) {
                        TextButton(
                            onClick = { telaLerEmailViewModel.onVerificaListaMarcadoresChange(true) },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Criar")
                        }
                    }
                    if (listaMarcadoresVazia and listaMarcadores.isNotEmpty()) {
                        TextButton(
                            onClick = { telaLerEmailViewModel.onVerificaListaMarcadoresChange(true) },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Voltar")
                        }
                    }
                    TextButton(
                        onClick = {
                            val marcador = Marcadores(
                                id = 0,
                                nome = bookmarkNomeField,
                                cor = bookmarkCorField.toInt()
                            )
                            marcadoresRepository.salvar(marcador)
                            telaLerEmailViewModel.onVerificaListaMarcadoresChange(false)
                            telaLerEmailViewModel.onListaDropdownChange(marcadoresRepository.listar())
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