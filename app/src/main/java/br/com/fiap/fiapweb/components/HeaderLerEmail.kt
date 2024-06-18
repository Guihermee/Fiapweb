package br.com.fiap.fiapweb.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import br.com.fiap.fiapweb.Repository.EmailRepository
import br.com.fiap.fiapweb.Repository.MarcadoresRepository
import br.com.fiap.fiapweb.model.Categoria
import br.com.fiap.fiapweb.model.Email
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel
import br.com.fiap.fiapweb.viewModel.TelaLerEmailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderLerEmail(
    onClickVoltar: () -> Unit,
    telaLerEmailViewModel: TelaLerEmailViewModel,
    telaInicialViewModel: TelaInicialViewModel,
    navController: NavController,
    email: Email,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {

    val context = LocalContext.current
    val marcadoresRepository = MarcadoresRepository(context)
    val listaMarcadores = marcadoresRepository.listar()
    val listaMarcadorSemGenerico = listaMarcadores.filterNot { it.nome == "Generico" }
    val verificaListaMarcador = listaMarcadorSemGenerico.isEmpty()

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        title = {
            Text(text = "")
        },
        navigationIcon = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onClickVoltar) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {
                if (verificaListaMarcador) {
                    telaLerEmailViewModel.onModalCriarStateChange(true)
                } else {
                    telaLerEmailViewModel.onModalAdicionarStateChange(true)
                }

            }
            ) {
                Icon(
                    imageVector = Icons.Outlined.BookmarkAdd,
                    contentDescription = "Bookmark Icon"
                )
            }
            IconButton(onClick = { navController.navigate("telaAdicionarEvento") }) {
                Icon(
                    imageVector = Icons.Outlined.EditCalendar,
                    contentDescription = "Adicionar Evento ao Calendário",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = {
                telaInicialViewModel.changeEmailCategoria(
                    context,
                    email,
                    Categoria.LIXEIRA
                )
                scope.launch {
                    snackbarHostState.showSnackbar("Email movido para a Lixeira com sucesso!")
                }
                navController.navigate("telaInicial")
            }) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}
