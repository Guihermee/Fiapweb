package br.com.fiap.fiapweb.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.fiapweb.model.Email

@Composable
fun ResumoEmail(email: Email, onCLick: () -> Unit, onLongClick: () -> Unit) {

    Row {

        // Nome, titulo e preview do conteúdo do email
        Column {
            Text(
                text = email.remetente,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .width(300.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,

                )
            Text(
                text = email.subject,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .width(300.dp),
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1
            )
            Text(
                text = email.destinatario,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .width(300.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1
            )

        }

        // Mais e a Data
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
                    modifier = Modifier.padding(horizontal = 6.dp),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Mais
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Outlined.MoreHoriz,
                        contentDescription = "More Icon",
                        modifier = Modifier
                            .size(20.dp)
                    )
                }


            }

        }
    }
}
