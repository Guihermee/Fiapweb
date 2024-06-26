package br.com.fiap.fiapweb.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.fiap.fiapweb.R

@Composable
fun ModalHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            Arrangement.Start
        ) {
            // Botão Perfil
            IconButton(
                onClick = { /*TODO botao perfil*/ },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user_image),
                    contentDescription = "Foto do Usuário",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(64.dp)
                )
            }
            // Email atual do usuario
            Column {
                Text(
                    text = "Ana Maria",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "ana@email.com",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ModalItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ModalPerfil(
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                //.height(360.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .clickable(onClick = {})
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(
                            MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                        .width(300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ModalHeader()
                    Divider(color = Color.Gray, thickness = 1.dp)
                    ModalItem(
                        icon = Icons.Default.PersonAddAlt1,
                        label = "Adicionar outra conta",
                        onClick = { }
                    )
                    ModalItem(
                        icon = Icons.Default.Person,
                        label = "Gerenciar minhas contas",
                        onClick = { }
                    )
                    ModalItem(
                        icon = Icons.Default.Settings,
                        label = "Configurações gerais",
                        onClick = {}
                    )
                    ModalItem(
                        icon = Icons.Default.Email,
                        label = "Gerar e-mail temporário",
                        onClick = {}
                    )
                    Divider(color = Color.Gray, thickness = 1.dp)
                    Text(
                        text = "Emails temporários",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                    ModalItem(
                        icon = Icons.Default.Email,
                        label = "maria2654@email.com",
                        onClick = {}
                    )
                    ModalItem(
                        icon = Icons.Default.Email,
                        label = "mariana04@email.com",
                        onClick = {}
                    )
                    Divider(color = Color.Gray, thickness = 1.dp)
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Fechar")
                    }
                }
            }
        }
    }
}