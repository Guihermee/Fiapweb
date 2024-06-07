package br.com.fiap.fiapweb.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.fiapweb.model.Email
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmailView(email: Email, onCLick: () -> Unit, onLongClick: () -> Unit, telaInicialViewModel: TelaInicialViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(if (email.isSelected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.background)
            .combinedClickable(
                onClick = onCLick,
                onLongClick = onLongClick,
                onLongClickLabel = "Email"
            )
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row {

            // Esse é a "foto" do email
            if (email.isSelected) {
                Column(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .clip(CircleShape)
                        .clickable { /*TODO*/ },
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFEFB8C8)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = Icons.Outlined.Check, contentDescription = "Check Incon")
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .clip(CircleShape)
                        .clickable { /*TODO*/ },
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFEFB8C8)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = email.remetente.first().uppercase(), fontSize = 30.sp)
                    }
                }
            }



            // Nome, titulo e preview do conteúdo do email
            Column {
                Text(
                    text = email.remetente,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .width(300.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (email.isRead) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,

                    )
                Text(
                    text = email.subject,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .width(300.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (email.isRead) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
                Text(
                    text = email.body,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .width(300.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (email.isRead) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
            }
        }

        // Estrela e a Data
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                // Data
                Text(
                    text = formatDate(email.timestamp),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Estrela
                IconButton(onClick = { /*TODO*/ }) {
                    if (email.isFavorite) {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "Star Icon",
                            modifier = Modifier.size(40.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.StarOutline,
                            contentDescription = "Star Icon",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
    }
}

fun formatDate(dateTime: LocalDateTime): String {
    // Formatação da data para o formato desejado
    val formatter = DateTimeFormatter.ofPattern("MMM dd", Locale("pt", "BR"))

    // Retorna a data formatada
    return dateTime.format(formatter)
}