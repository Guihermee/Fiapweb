package br.com.fiap.fiapweb.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.fiapweb.model.Email
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun EmailView(email: Email, onCLick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
//            .background(Color.Red)
            .clickable(onClick = onCLick)
            .padding(horizontal = 8.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row {
            // Esse é a "foto" do email
            Column(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFEFB8C8)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "N", fontSize = 30.sp)
                }
            }

            // Titulo
            Column {
                Text(
                    text = email.remetente,
                    modifier = Modifier.padding(horizontal = 8.dp).width(300.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,

                )
                Text(
                    text = email.subject,
                    modifier = Modifier.padding(horizontal = 8.dp).width(300.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
                Text(
                    text = email.body,
                    modifier = Modifier.padding(horizontal = 8.dp).width(300.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
            }
        }

        Column(
            modifier = Modifier
//                .background(Color.Blue)
                .fillMaxHeight()
                .padding(vertical = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = formatDate(email.timestamp),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(20.dp))

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