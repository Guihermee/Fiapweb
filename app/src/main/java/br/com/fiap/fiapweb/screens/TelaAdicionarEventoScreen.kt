package br.com.fiap.fiapweb.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.fiapweb.viewModel.AdicionarEventoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdicionarEventoScreen(
    navController: NavController,
    adicionarEventoViewModel: AdicionarEventoViewModel
) {
    val context = LocalContext.current

    // State para armazenar os valores dos campos
    var titulo by remember { mutableStateOf(TextFieldValue()) }
    var local by remember { mutableStateOf(TextFieldValue()) }
    var descricao by remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    adicionarEventoViewModel.adicionarEventoAoCalendario(titulo.text, local.text, descricao.text)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Enviar")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text(text = "Adicionar Evento ao Calendário") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = local,
                    onValueChange = { local = it },
                    label = { Text("Local") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
