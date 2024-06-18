package br.com.fiap.fiapweb.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ManageSearch
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.fiapweb.R
import br.com.fiap.fiapweb.Repository.EmailRepository
import br.com.fiap.fiapweb.Repository.HistoricoDeBuscaRespository
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarHeader(
    telaInicialViewModel: TelaInicialViewModel,
    onSearch: (String) -> Unit,
    couroutineScope: CoroutineScope,
    drawerState: DrawerState
) {

    val textFieldValue by telaInicialViewModel.textField.observeAsState(initial = "")
    val isSearching by telaInicialViewModel.isSearching.collectAsState()

    // Instância do HistoricoRepository
    val context = LocalContext.current
    val historicoRepository = HistoricoDeBuscaRespository(context)

    // Barra de pesquisa
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = if (!isSearching) 16.dp else 0.dp,
                end = if (!isSearching) 16.dp else 0.dp,
                top = if (!isSearching) 8.dp else 0.dp
            ),
        query = textFieldValue,
        onQueryChange = { telaInicialViewModel.onTextFieldChange(it) },
        onSearch = onSearch,
        active = isSearching,
        onActiveChange = { telaInicialViewModel.onToogleSearch() },
        placeholder = { Text(text = "Pesquisar no Email") },
        leadingIcon = {
            if (isSearching) {
                IconButton(onClick = { telaInicialViewModel.onToogleSearch() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Icone de Voltar"
                    )
                }
            } else {
                IconButton(
                    onClick = { couroutineScope.launch { drawerState.open() } },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_thre_line),
                        contentDescription = "Icone do sidebar"
                    )
                }
            }
        },
        trailingIcon = {
            if (isSearching) {
                IconButton(onClick = { telaInicialViewModel.clearTextField() }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Icone Limpar Campo Digitado"
                    )
                }
            } else {
                IconButton(
                    onClick = {
                        telaInicialViewModel.onshowDialogPerfilChange(true)
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user_image),
                        contentDescription = "Foto do Usuário",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    ) {
        val listaHistoricoDb = historicoRepository.db.listarHistorico()
        val listaHistorico by telaInicialViewModel.listaHistorico.observeAsState(initial = listaHistoricoDb)

        if (textFieldValue == "") {
            // Mini Texto quando Usuario não pesquisar
            Text(
                text = "Recentes emails pesquisados",
                modifier = Modifier.padding(8.dp),
                fontSize = 12.sp
            )
            // Percorrendo a Lista do banco de dados local dos itens já pesquisados
            listaHistorico.forEach { historicoItem ->
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .fillMaxWidth()
                        .clickable {
                            telaInicialViewModel.onTextFieldChange(historicoItem.pesquisa)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "Icone do historico",
                        )
                        Text(
                            text = historicoItem.pesquisa,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    IconButton(onClick = {
                        historicoRepository.db.deletar(historicoItem)
                        telaInicialViewModel.onListaHistoricoChange(historicoRepository.db.listarHistorico())
                    }) {
                        Icon(imageVector = Icons.Outlined.Clear, contentDescription = "Clear Icon")
                    }
                }
            }
        } else {
            Row(modifier = Modifier.padding(16.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ManageSearch,
                    contentDescription = "ManageSearch Icon"
                )
                Text(
                    text = "Pesquisar por '${textFieldValue}' no email",
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}
