package br.com.fiap.fiapweb.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.fiap.fiapweb.Repository.EmailRepository
import br.com.fiap.fiapweb.Repository.MarcadoresRepository
import br.com.fiap.fiapweb.model.Marcadores
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ModalItems(label: String, onClick: () -> Unit, selected: Boolean) {
//    FilterChip(
//        onClick = onClick,
//        label = {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp, horizontal = 16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = label,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Medium
//                )
//            }
//        },
//        selected = selected
//    )
//}
//
//@Composable
//fun ModalFiltros(
//    onDismissRequest: () -> Unit,
//    onAplicarRequest: () -> Unit
//) {
///    val context = LocalContext.current
///   val marcadoresRepository = MarcadoresRepository(context);
///  val items = remember {
///     mutableListOf(marcadoresRepository.listar())
///   }
//    val items = listOf("Urgentes", "Trabalho", "Estudos")
//    val selectedStates = remember {
//        mutableStateMapOf<String, Boolean>().apply {
//            items.forEach { put(it, false) }
//        }
//    }
//
//    Dialog(onDismissRequest = {
//        onDismissRequest()
//    }) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            shape = RoundedCornerShape(16.dp),
//        ) {
//            Box(
//                modifier = Modifier
//                    .clickable(onClick = {})
//            ) {
//                Column(
//                    modifier = Modifier
//                        .align(Alignment.Center)
//                        .background(
//                            MaterialTheme.colorScheme.background,
//                            shape = RoundedCornerShape(16.dp)
//                        )
//                        .padding(16.dp)
//                        .width(300.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.spacedBy(16.dp)
//                ) {
//                    Text(
//                        text = "Filtros",
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Divider(color = Color.Gray, thickness = 1.dp)
//
//                    items.forEach { label ->
//                        ModalItems(
//                            label = label,
//                            onClick = {
//                                selectedStates[label] = !(selectedStates[label] ?: false)
//                            },
//                            selected = selectedStates[label] ?: false
//                        )
//                    }
//
//                    Divider(color = Color.Gray, thickness = 1.dp)
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        TextButton(
//                            onClick = { onDismissRequest() },
//                            modifier = Modifier.padding(8.dp),
//                        ) {
//                            Text("Fechar")
//                        }
//                        TextButton(
//                            onClick = onAplicarRequest
//                        ) {
//                            Text(text = "Aplicar")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//////////////////////////////////////////////////////////////////////////////////////


@Composable
fun ModalFiltros(
    onDismissRequest: () -> Unit,
    telaInicialViewModel: TelaInicialViewModel
) {
    val context = LocalContext.current
    val marcadoresRepository = MarcadoresRepository(context)
    val items = remember {
        marcadoresRepository.listar()
            .toMutableList() // Atribui diretamente a lista de itens do repositório
    }
    val id: Long = 0
    val emailRepository = EmailRepository(context)
//    val listaDeEmailsMarcados = remember {
//        emailRepository.listarEmailsPeloMarcador(id = id)
//    }

    // Crie uma lista de ações dinamicamente baseada na lista de itens
//    val actions: List<() -> Unit> = items.mapIndexed { index, item ->
//        {
//            if (index == id.toInt()) {
//
//            }
//            // Defina a ação específica para cada item aqui
//            // Pode usar 'index' e 'item' para diferenciar a ação
//            //println("Ação para item $index: $item")
//        }
//    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                modifier = Modifier.clickable(onClick = {})
            ) {
                Text(
                        text = "Filtros",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                Divider(color = Color.Gray, thickness = 1.dp)
                LazyColumn(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                        .width(300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(items.size) { item ->
                        Botao(
                            items,
                            text = items[item].nome,
                            onClick = {
                                items.mapIndexed { index, marcadores ->
                                    if (index == item) {

                                        var emailMarcadore = emailRepository.listarEmailsPeloMarcador(marcadores.id)
                                        telaInicialViewModel.onListaCompletaEmailDbChange(emailMarcadore)

                                    }
                                }

                            }
                        )
                    }
                }
                Divider(color = Color.Gray, thickness = 1.dp)
                TextButton(
                            onClick = { onDismissRequest() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Fechar")
                        }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ModalPreviw() {
    ModalFiltros({}, telaInicialViewModel = TelaInicialViewModel())
}

@Composable
fun Botao(
    items: MutableList<Marcadores>,
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text)
    }
}

