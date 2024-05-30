package br.com.fiap.fiapweb.components

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.fiap.fiapweb.R
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarHeader(telaInicialViewModel: TelaInicialViewModel) {

    val textFieldValue by telaInicialViewModel.textField.observeAsState(initial = "")
    val isSearching by telaInicialViewModel.isSearching.collectAsState()
    val items = remember {
        mutableListOf(
            "Kabum",
            "Fiap"
        )
    }

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
        onSearch = {
            // isso é um exemplo de como manipularia o History de pesquisa
            items.add(textFieldValue)
            telaInicialViewModel.setIsSearchingToFalse()
        },
        active = isSearching,
        onActiveChange = { telaInicialViewModel.onToogleSearch() },
        leadingIcon = {
            if (isSearching) {
                IconButton(onClick = { telaInicialViewModel.setIsSearchingToFalse() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Icone de Voltar")
                }
            } else {
                IconButton(
                    onClick = { /*TODO botao sidebar*/ },
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
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Icone Limpar Campo Digitado")
                }
            } else {
                IconButton(
                    onClick = { /*TODO botao perfil*/ },
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
        // Aqui vai o lazy column caso queira que aparece aqui, mas no gmail aqui aparece outra coisa
        // (olha ai no seu app pra ver, explicar vai dmr e to com preguiça

        // TODO local database para guardar itens já pesquisados
        // Exemplo abaixo:
        items.forEach {
            Row(modifier = Modifier.padding(16.dp)) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "Icone do historico"
                )
                Text(text = it)
            }
        }

    }
}