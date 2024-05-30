package br.com.fiap.fiapweb.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import br.com.fiap.fiapweb.components.SearchBarHeader
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel

@Composable
fun TelaInicialScreen(
    navController: NavController,
    telaInicialViewModel: TelaInicialViewModel
) {
    Box {
        Column {
            SearchBarHeader(telaInicialViewModel)
        }
    }
}