package br.com.fiap.fiapweb.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.fiapweb.components.HeaderLerEmail
import br.com.fiap.fiapweb.components.ModalMarcadoresAdicionar
import br.com.fiap.fiapweb.components.ModalMarcadoresCriar
import br.com.fiap.fiapweb.components.ModalOpenAIResume
import br.com.fiap.fiapweb.components.formatDate
import br.com.fiap.fiapweb.model.Email
import br.com.fiap.fiapweb.service.getOpenAICompletion
import br.com.fiap.fiapweb.utils.Converters
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel
import br.com.fiap.fiapweb.viewModel.TelaLerEmailViewModel
import kotlinx.coroutines.launch

@Composable
fun TelaLerEmailScreen(
    navController: NavController,
    telaLerEmailViewModel: TelaLerEmailViewModel,
    telaInicialViewModel: TelaInicialViewModel,
    emailString: String
) {
    val modalCriarState by telaLerEmailViewModel.modalCriarState.observeAsState(initial = false)
    val modalAdiconarState by telaLerEmailViewModel.modalAdicionarState.observeAsState(initial = false)
    val modalAIGenerateResumeState by telaLerEmailViewModel.modalAIGenerateResumeState.observeAsState(
        initial = false
    )
    val responseResume by telaLerEmailViewModel.responseResume.observeAsState(initial = "")
    val email = Converters().jsonToEmail(emailString)

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            HeaderLerEmail(
                onClickVoltar = { navController.navigate("telaInicial") },
                telaLerEmailViewModel,
                telaInicialViewModel,
                navController,
                email,
                snackbarHostState = snackbarHostState,
                scope = scope
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                telaLerEmailViewModel.onModalAIGenerateResumeStateChange(
                    true
                )
            }) {
                Icon(imageVector = Icons.Outlined.AutoAwesome, contentDescription = "Generate Icon")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {

            // Modais dos Marcadores
            if (modalCriarState) {
                ModalMarcadoresCriar(onDismissRequest = {
                    telaLerEmailViewModel.onModalCriarStateChange(
                        false
                    )
                }, telaLerEmailViewModel)
            }

            // Modal da AI
            if (modalAIGenerateResumeState) {
                ModalOpenAIResume(
                    onDismissRequest = {
                        telaLerEmailViewModel.onModalAIGenerateResumeStateChange(
                            false
                        )
                    },
                    onConfirmation = {
                        scope.launch {
                            val prompt = "Gere um resumo curto do seguinte Email: ${email.body}"
                            val response = getOpenAICompletion(prompt = prompt)

                            telaLerEmailViewModel.onResponseResumeChange(response)
                        }
                    },
                    response = responseResume
                )
            }

            if (modalAdiconarState) {
                ModalMarcadoresAdicionar(
                    onDismissRequest = {
                        telaLerEmailViewModel.onModalAdicionarStateChange(
                            false
                        )
                    },
                    telaLerEmailViewModel = telaLerEmailViewModel,
                    email = email,
                    snackbarHostState = snackbarHostState,
                    scope = scope
                )
            }

            // Construção do Email
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                EmailHeader(email)
                Spacer(modifier = Modifier.height(16.dp))
                EmailBody(email)
                Spacer(modifier = Modifier.weight(1f))
                EmailFooter(email)
            }
        }
    }
}

// Componentes dos Emails
@Composable
fun EmailHeader(email: Email) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            EmailAvatar(email)
            EmailDetails(email)
        }
        EmailTimestamp(email)
    }
}

@Composable
fun EmailAvatar(email: Email) {
    if (email.isSelected) {
        AvatarIcon(
            icon = Icons.Outlined.Check,
            backgroundColor = Color(0xFFEFB8C8)
        )
    } else {
        AvatarText(
            text = email.remetente.first().uppercase(),
            backgroundColor = Color(0xFFEFB8C8)
        )
    }
}

@Composable
fun AvatarIcon(icon: ImageVector, backgroundColor: Color) {
    Column(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { /*TODO*/ },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = icon, contentDescription = null)
    }
}

@Composable
fun AvatarText(text: String, backgroundColor: Color) {
    Column(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { /*TODO*/ },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text, fontSize = 30.sp)
    }
}

@Composable
fun EmailDetails(email: Email) {
    Column {
        Text(
            text = email.remetente,
            modifier = Modifier
                .padding(start = 8.dp)
                .width(300.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = if (email.isRead) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onBackground,
            maxLines = 1
        )
        Text(
            text = email.destinatario,
            modifier = Modifier
                .padding(start = 8.dp)
                .width(300.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = if (email.isRead) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onBackground,
            maxLines = 1
        )
    }
}


@Composable
fun EmailTimestamp(email: Email) {
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = formatDate(email.timestamp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.MoreHoriz,
                contentDescription = "More Icon",
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

@Composable
fun EmailBody(email: Email) {
    Text(
        text = email.body,
        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun EmailFooter(email: Email) {
    Column {
        if (email.attachments.isNotEmpty()) {
            Text(
                text = "Assinatura",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.AttachFile,
                    contentDescription = "Anexos",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Anexos: ${email.attachments.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { /* TODO Ação para responder a todos */ },
                modifier = Modifier.clip(CircleShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "Responder",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
        }
    }
}
