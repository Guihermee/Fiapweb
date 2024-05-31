package br.com.fiap.fiapweb.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.fiapweb.R

//Essa parte deve ser implementada por quem for chamar o modal

//    var showModal by remember { mutableStateOf(false) }
//
//        Button(onClick = { showModal = true }) {
//            Text("Show Modal")
//        }
//
//        if (showModal) {
//            ModalDialog(onDismiss = { showModal = false })
//        }




@Composable
fun ModalDialogPerfil(onDismiss: () -> Unit) {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(onClick = onDismiss)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
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
                onClick = onDismiss
            )
            ModalItem(
                icon = Icons.Default.Person,
                label = "Gerenciar minhas contas",
                onClick = onDismiss
            )
            ModalItem(
                icon = Icons.Default.Settings,
                label = "Configurações gerais",
                onClick = onDismiss
            )
            ModalItem(
                icon = Icons.Default.Email,
                label = "Gerar e-mail temporário",
                onClick = onDismiss
            )
            Divider(color = Color.Gray, thickness = 1.dp)
            Text(
                text = "Emails temporários",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            ModalItem(
                icon = Icons.Default.Email,
                label = "maria2654@email.com - 9 min",
                onClick = onDismiss
            )
            ModalItem(
                icon = Icons.Default.Email,
                label = "mariana04@email.com - 2 min",
                onClick = onDismiss
            )
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                Text("Fechar")
            }
        }
    }
}

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

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun SidebarPreviw() {
//    ModalDialog() {}
//}