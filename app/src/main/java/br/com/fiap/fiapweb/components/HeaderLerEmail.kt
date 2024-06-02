package br.com.fiap.fiapweb.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.fiapweb.R

@Composable
fun HeaderEscreverEmail(textContent: String, onClickVoltar: () -> Unit) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(id = R.color.cinza))
                    .height(50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClickVoltar,
                        modifier = Modifier.padding(start = 16.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "Icone de voltar",
                            tint = colorResource(id = R.color.cinza_escuro)
                        )
                    }

                    Text(
                        text = textContent,
                        modifier = Modifier.padding(start = 16.dp),
                        color = colorResource(id = R.color.cinza_escuro),
                        fontSize =  20.sp
                    )
                }

                Icon(
                    painter = painterResource(id = R.drawable.outline_delete_outline_24),
                    contentDescription = "Ícone de lixo",
                    modifier = Modifier.padding(end = 18.dp),
                    tint = colorResource (id = R.color.cinza_escuro)
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_more_vert_24),
                    contentDescription = "Ícone de mais",
                    modifier = Modifier.padding(end = 16.dp),
                    tint = colorResource (id = R.color.cinza_escuro)
                )

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
