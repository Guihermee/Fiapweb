package br.com.fiap.fiapweb.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelecionadosHeader(
    telaInicialViewModel: TelaInicialViewModel,
    qtdEmailSelecionado: Int,
    onDeleteClick: () -> Unit,
    iconDraft: ImageVector,
    onDraftClick: () -> Unit,
    onSelectAllClick: () -> Unit,
    todosEmailSelecionados: Boolean

) {
    val context = LocalContext.current

    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        title = {
            TextButton(onClick = onSelectAllClick) {
                Icon(
                    imageVector = if (todosEmailSelecionados) Icons.Outlined.CheckBox else Icons.Outlined.CheckBoxOutlineBlank,
                    contentDescription = "CheckBox empty Icon"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = if (todosEmailSelecionados) "Desselecionar todos" else "Seleciona Todos")
            }

        },
        navigationIcon = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    telaInicialViewModel.onSelectedChange(false)
                    telaInicialViewModel.changeAllEmailSelectTo(context, false)
                    telaInicialViewModel.onQtdEmailSelecionada(1)
                    telaInicialViewModel.onTodosEmailSelecionadosChange(false)
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "$qtdEmailSelecionado",
                    fontSize = 24.sp
                )
            }

        },
        actions = {
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onDraftClick) {
                Icon(
                    imageVector = iconDraft,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }
        }
    )
}