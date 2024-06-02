package br.com.fiap.fiapweb.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import br.com.fiap.fiapweb.components.HeaderLerEmail
import br.com.fiap.fiapweb.viewModel.TelaLerEmailViewModel

@Composable
fun TelaLerEmailScreen(
    navController: NavController,
    telaLerEmailViewModel: TelaLerEmailViewModel
) {
    Scaffold(
        topBar = { HeaderLerEmail(textContent = "") {} },

        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("") })
            { Icon(imageVector = Icons.Outlined.AutoAwesome, contentDescription = "Generate Icon") }
        }

    ) {
            contentPadding ->
        Box(modifier = Modifier.padding(contentPadding))}

}