package br.com.fiap.fiapweb.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.fiap.fiapweb.Repository.EmailRepository
import br.com.fiap.fiapweb.Repository.MarcadoresRepository
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel

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
    val verificarMarcadoresEmpty = items.filterNot { it.nome == "Generico" }.isEmpty()
    val emailRepository = EmailRepository(context)

    val marcadorVazio by telaInicialViewModel.marcadorVazio.observeAsState(initial = false)

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxSize().padding(8.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    if (!verificarMarcadoresEmpty) {
                        Text(
                            text = "Filtros",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (marcadorVazio) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Warning Icon",
                                tint = MaterialTheme.colorScheme.error
                            )
                            Text(
                                text = "Não existe nenhum Email com esse Filtro",
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    if (!verificarMarcadoresEmpty) {
                        Divider(color = Color.Gray, thickness = 1.dp)
                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp)
                                .width(300.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            items(items.size) { item ->
                                Botao(
                                    text = items[item].nome,
                                    onClick = {
                                        items.mapIndexed { index, marcadores ->
                                            if (index == item) {

                                                val emailMarcadores =
                                                    emailRepository.listarEmailsPeloMarcador(
                                                        marcadores.id
                                                    )


                                                if (emailMarcadores.isEmpty()) {
                                                    telaInicialViewModel.onMarcadorVazioChange(!marcadorVazio)
                                                } else {
                                                    telaInicialViewModel.onMarcadorVazioChange(false)
                                                    telaInicialViewModel.onListaCompletaEmailDbChange(
                                                        emailMarcadores
                                                    )
                                                    telaInicialViewModel.onshowDialogFiltrosChange(false)
                                                }
                                            }
                                        }
                                    }
                                )
                            }
                        }
                        Divider(color = Color.Gray, thickness = 1.dp)
                    }
                    if (verificarMarcadoresEmpty) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Warning Icon",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "Parece que você não tem nenhum Marcador, por favor entre em um Email e adicione um.",
                            textAlign = TextAlign.Center
                        )
                    }
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
}

@Composable
fun Botao(
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
