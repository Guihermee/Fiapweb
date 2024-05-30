package br.com.fiap.fiapweb.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.fiap.fiapweb.R
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel

@Composable
fun SearchBarHeader(telaInicialViewModel: TelaInicialViewModel) {

    val textFieldValue by telaInicialViewModel.textField.observeAsState(initial = "")

    TextField(
        value = textFieldValue,
        onValueChange = {
            telaInicialViewModel.onTextFieldChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp),
        shape = RoundedCornerShape(40.dp),
        placeholder = { Text(text = "Pesquisar no Email") },
        leadingIcon = {
            IconButton(
                onClick = { /*TODO botao sidebar*/ },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_thre_line),
                    contentDescription = "Icone do sidebar"
                )
            }
        },
        trailingIcon = {
            IconButton(
                onClick = { /*TODO botao perfil*/ },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user_image),
                    contentDescription = "Foto do Usuário",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
//            unfocusedContainerColor = colorResource(id = R.color.cinza),
//            focusedContainerColor = colorResource(id = R.color.cinza)
        )
    )
}