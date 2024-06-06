package br.com.fiap.fiapweb.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.fiap.fiapweb.R

@Composable
fun SendButton(
    modifier: Modifier
) {
    Column (modifier = Modifier
        .fillMaxWidth()
        .height(55.dp)
        .width(150.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom){
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text(text = "ENVIAR")
            Icon(modifier = Modifier.padding(horizontal = 8.dp),
                painter = painterResource(id = R.drawable.baseline_send_24),
                contentDescription ="Icone de enviar",
                tint = colorResource(id = R.color.white))
        }
    }
}