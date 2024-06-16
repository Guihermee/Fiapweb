package br.com.fiap.fiapweb.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import br.com.fiap.fiapweb.components.CorpoDoEMail
import br.com.fiap.fiapweb.components.HeaderLerEmail
import br.com.fiap.fiapweb.components.ModalMarcadores
import br.com.fiap.fiapweb.components.ResumoEmail
import br.com.fiap.fiapweb.model.Email
import br.com.fiap.fiapweb.model.Priority
import br.com.fiap.fiapweb.viewModel.TelaLerEmailViewModel
import java.time.LocalDateTime

@Composable
fun TelaLerEmailScreen(
    navController: NavController,
    telaLerEmailViewModel: TelaLerEmailViewModel
) {

    val bookMarkState by telaLerEmailViewModel.bookMarkState.observeAsState(initial = false)

    Scaffold(
        topBar = {
            HeaderLerEmail(
                onClickVoltar = {},
                telaLerEmailViewModel = telaLerEmailViewModel,
                navController = navController)
        },

        floatingActionButton = {
            FloatingActionButton(onClick = { /*navController.navigate("")*/ })
            { Icon(imageVector = Icons.Outlined.AutoAwesome, contentDescription = "Generate Icon") }
        }

    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {

            // Modal Book Mark
            if (bookMarkState) {
                ModalMarcadores(
                    onDismissRequest = { telaLerEmailViewModel.onBookMarkStateChange(false) },
                    onConfirmation = {},
                    telaLerEmailViewModel
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(vertical = 50.dp)

            ) {
                ResumoEmail(email = emailExemplo, onCLick = { /*TODO*/ }) {
                }

                //INSERIR O BODY DO EMAIL

            }
            Column(modifier = Modifier.fillMaxSize()) {
                CorpoDoEMail()
            }
        }
    }
}

val emailExemplo = Email(
    id = 0,
    remetente = "remetente@example.com",
    destinatario = "destinatario@example.com",
    cc = listOf("cc@example.com"),
    bcc = listOf("bcc@example.com"),
    subject = "Assunto",
    body = "email@example.com, email2@example.com", //precisa apontar para o cc
    attachments = listOf("anexo.pdf"),
    timestamp = LocalDateTime.now(),
    isRead = false,
    isFavorite = false,
    priority = Priority.NORMAL,
    isSelected = false,
    marcadorId = 1
)
