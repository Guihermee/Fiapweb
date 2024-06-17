package br.com.fiap.fiapweb.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import br.com.fiap.fiapweb.components.ModalMarcadores
import br.com.fiap.fiapweb.components.formatDate
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
            //HeaderLerEmail(onClickVoltar = { navController.navigate("telaInicial") }, telaLerEmailViewModel)
            HeaderLerEmail(onClickVoltar = { navController.navigate("telaInicial") }, telaLerEmailViewModel, navController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*  */ }) {
                Icon(imageVector = Icons.Outlined.AutoAwesome, contentDescription = "Generate Icon")
            }
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
//            if (bookMarkState) {
//                ModalMarcadores(onDismissRequest = { telaLerEmailViewModel.onBookMarkStateChange(false) })
//            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                EmailHeader()
                Spacer(modifier = Modifier.height(16.dp))
                EmailBody()
                Spacer(modifier = Modifier.weight(1f))
                EmailFooter()
            }
        }
    }
}

@Composable
fun EmailHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            EmailAvatar()
            EmailDetails()
        }
        EmailTimestamp()
    }
}

@Composable
fun EmailAvatar() {
    if (emailExemplo.isSelected) {
        AvatarIcon(
            icon = Icons.Outlined.Check,
            backgroundColor = Color(0xFFEFB8C8)
        )
    } else {
        AvatarText(
            text = emailExemplo.remetente.first().uppercase(),
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
fun EmailDetails() {
    Column {
        Text(
            text = emailExemplo.remetente,
            modifier = Modifier
                .padding(start = 8.dp)
                .width(300.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = if (emailExemplo.isRead) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onBackground,
            maxLines = 1
        )
        Text(
            text = emailExemplo.destinatario,
            modifier = Modifier
                .padding(start = 8.dp)
                .width(300.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = if (emailExemplo.isRead) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onBackground,
            maxLines = 1
        )
    }
}


@Composable
fun EmailTimestamp() {
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = formatDate(emailExemplo.timestamp),
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
fun EmailBody() {
    Text(
        text = emailExemplo.body,
        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun EmailFooter() {
    Column(
    ) {
        if (emailExemplo.attachments.isNotEmpty()) {
            Text(
                text = "Assinatura",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AttachFile, contentDescription = "Anexos", tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Anexos: ${emailExemplo.attachments.joinToString(", ")}",
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
                onClick = { /* Ação para responder a todos */ },
                modifier = Modifier.clip(CircleShape)
            ) {
                Text(
                    text = "Responder",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
fun EmailDetailRow(icon: ImageVector, text: String) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
            )
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

val emailExemplo = Email(
    id = 0,
    remetente = "Ceriani Almeida",
    destinatario = "Para: gr-gerentes",
    cc = listOf("cc@example.com"),
    bcc = listOf("bcc@example.com"),
    subject = "Assunto",
    body = """
        Prezado(a),

        Espero que esta mensagem o(a) encontre bem. Gostaria de compartilhar algumas atualizações importantes sobre nosso projeto.

        Aqui estão alguns dos pontos mais recentes:
        - Concluímos a fase de design e estamos avançando para a implementação.
        - A integração com o sistema legado foi bem-sucedida, permitindo uma transição suave.
        - Realizamos várias sessões de teste de usuário e o feedback tem sido positivo.
        - Concluímos a fase de design e estamos avançando para a implementação.
        - A integração com o sistema legado foi bem-sucedida, permitindo uma transição suave.

        """.trimIndent(),
    attachments = listOf("anexo.pdf"),
    timestamp = LocalDateTime.now(),
    isRead = false,
    isFavorite = false,
    priority = Priority.NORMAL,
    isSelected = false,
    marcadorId = 1
)
