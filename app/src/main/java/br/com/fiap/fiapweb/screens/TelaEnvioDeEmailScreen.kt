package br.com.fiap.fiapweb.screens


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.fiapweb.components.EmailAdress
import br.com.fiap.fiapweb.components.EmailBody
import br.com.fiap.fiapweb.components.HeaderEscreverEmail
import br.com.fiap.fiapweb.components.ModalOpenAICompletion
import br.com.fiap.fiapweb.model.Categoria
import br.com.fiap.fiapweb.model.Email
import br.com.fiap.fiapweb.model.Priority
import br.com.fiap.fiapweb.Repository.EmailRepository
import br.com.fiap.fiapweb.service.getOpenAICompletion
import br.com.fiap.fiapweb.viewModel.EnvioDeEmailViewModel
import br.com.fiap.fiapweb.viewModel.ModalOpenAIViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun TelaEnvioDeEmailScreen(
    navController: NavController,
    envioDeEmailViewModel: EnvioDeEmailViewModel = viewModel(),
    modalOpenAIViewModel: ModalOpenAIViewModel
) {
    val toFieldValue by envioDeEmailViewModel.toFieldValue.observeAsState(initial = "")
    val subjectFieldValue by envioDeEmailViewModel.subjectFieldValue.observeAsState(initial = "")
    val emailBodyFieldValue by envioDeEmailViewModel.emailBodyFieldValue.observeAsState(initial = "")
    val ccFieldValue by envioDeEmailViewModel.ccFieldValue.observeAsState(initial = "")
    val ccoFieldValue by envioDeEmailViewModel.ccoFieldValue.observeAsState(initial = "")
    val modalOpenAICompletion by envioDeEmailViewModel.modalOpenAICompletion.observeAsState(initial = false)
    val responseAI by envioDeEmailViewModel.responseAI.observeAsState(initial = "")
    var showCcCcoFields by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val emailRepository = EmailRepository(context)

    // Estado para controlar exibição do modal de erro
    var showErrorModal by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }



    Scaffold(

        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingActionButton(
                    onClick = {
                              val emailEnviado = Email(
                                  id = 0,
                                  remetente = "email@teste.com.br",
                                  destinatario = toFieldValue,
                                  cc = ccFieldValue,
                                  bcc = ccoFieldValue,
                                  subject = subjectFieldValue,
                                  body = emailBodyFieldValue,
                                  attachments = emptyList(),
                                  timestamp = LocalDateTime.now(),
                                  isRead = false,
                                  isFavorite = false,
                                  priority = Priority.LOW,
                                  isSelected = false,
                                  categoria = Categoria.ENVIADOS,
                                  marcadorId = 1 )
                        emailRepository.salvar(emailEnviado)
                        navController.navigate("telaInicial")
                              },

                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
                }
                FloatingActionButton(
                    onClick = {
                        envioDeEmailViewModel.onModalOpenAICompletionChange(true)
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = "AI")
                }
            }
        },

        topBar = {
            HeaderEscreverEmail(
                textContent = "",
                onClickVoltar = { navController.navigate("telaInicial") },
                onClickAttachFile = {},
                onClickMoreVert = {}
            )
        }

    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {

            // Modal Open IA
            if (modalOpenAICompletion) {
                ModalOpenAICompletion(
                    onDismissRequest = { envioDeEmailViewModel.onModalOpenAICompletionChange(false) },
                    onConfirmation = {
                        scope.launch {
                            val response = getOpenAICompletion(prompt = modalOpenAIViewModel.promptValue.value!!)
                            val teste1 = modalOpenAIViewModel.promptValue.value!!
                            Log.i("FIAP", "TelaEnvioDeEmailScreen: ${teste1}")
                            envioDeEmailViewModel.onResponseAIChange(response)
                            val teste = modalOpenAIViewModel.promptValue.value!!
                            envioDeEmailViewModel.onEmailBodyFieldValueChanged(response)
                            envioDeEmailViewModel.onModalOpenAICompletionChange(false)
                        }
                    },
                    modalOpenAIViewModel = modalOpenAIViewModel
                )
            }

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
            }
        }
    }
}