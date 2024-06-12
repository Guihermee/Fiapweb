package br.com.fiap.fiapweb.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.fiapweb.components.EmailAdress
import br.com.fiap.fiapweb.components.EmailBody
import br.com.fiap.fiapweb.components.HeaderEscreverEmail
import br.com.fiap.fiapweb.components.SelecionadosHeader
import br.com.fiap.fiapweb.components.SendButton
import br.com.fiap.fiapweb.viewModel.EnvioDeEmailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaEnvioDeEmailScreen(
    navController: NavController,
    envioDeEmailViewModel: EnvioDeEmailViewModel
) {
    val toFieldValue by envioDeEmailViewModel.toFieldValue.observeAsState(initial = "")
    val subjectFieldValue by envioDeEmailViewModel.subjectFieldValue.observeAsState(initial = "")
    val emailBodyFieldValue by envioDeEmailViewModel.emailBodyFieldValue.observeAsState(initial = "")
    val ccFieldValue by envioDeEmailViewModel.ccFieldValue.observeAsState(initial = "")
    val ccoFieldValue by envioDeEmailViewModel.ccoFieldValue.observeAsState(initial = "")
    var showCcCcoFields by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {navController.navigate("telaLeituraEmail")}) {
                Icon(imageVector = Icons.Outlined.AutoAwesome, contentDescription = "Generate Icon")
            }
        },

        topBar = { HeaderEscreverEmail(textContent = "") {

        }}


    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Divider()

                EmailAdress(
                    value = toFieldValue,
                    modifier = Modifier,
                    keyboardType = KeyboardType.Email,
                    text = "Para: ",
                    updateValue = { envioDeEmailViewModel.onToFieldValueChanged(it) },
                    showDropDownIcon = true,
                    onDropDownClick = { showCcCcoFields = !showCcCcoFields }
                )
                Divider()

                if (showCcCcoFields) {
                    EmailAdress(
                        value = ccFieldValue,
                        modifier = Modifier,
                        keyboardType = KeyboardType.Email,
                        text = "Cc: ",
                        updateValue = { envioDeEmailViewModel.onCcFieldValueChanged(it) }
                    )
                    Divider()

                    EmailAdress(
                        value = ccoFieldValue,
                        modifier = Modifier,
                        keyboardType = KeyboardType.Email,
                        text = "Cco: ",
                        updateValue = { envioDeEmailViewModel.onCcoFieldValueChanged(it) }
                    )
                    Divider()
                }


                EmailAdress(
                    value = subjectFieldValue,
                    modifier = Modifier,
                    keyboardType = KeyboardType.Text,
                    text = "Assunto: ",
                    updateValue = { envioDeEmailViewModel.onSubjectFieldValueChanged(it) }
                )
                Divider()


                EmailBody(
                    value = emailBodyFieldValue,
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardType = KeyboardType.Text,
                    updateValue = { envioDeEmailViewModel.onEmailBodyFieldValueChanged(it) }
                )

                SendButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
        }
    }
}