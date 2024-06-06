package br.com.fiap.fiapweb.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CorpoDoEMail() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            emailExemplo

        }
    }
}

const val emailExemplo = "asudhasiudasifhjasoidjasoiduashfasdosaihdoaisjdsaoidjasoidjasodijasiod"