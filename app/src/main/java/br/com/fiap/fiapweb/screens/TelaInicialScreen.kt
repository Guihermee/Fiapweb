package br.com.fiap.fiapweb.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.fiapweb.R
import br.com.fiap.fiapweb.components.EmailView
import br.com.fiap.fiapweb.components.ModalFiltros
import br.com.fiap.fiapweb.components.ModalPerfil
import br.com.fiap.fiapweb.components.NavigationItemView
import br.com.fiap.fiapweb.components.SearchBarHeader
import br.com.fiap.fiapweb.components.SelecionadosHeader
import br.com.fiap.fiapweb.model.Email
import br.com.fiap.fiapweb.model.NavigationItem
import br.com.fiap.fiapweb.model.Priority
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel
import java.time.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaInicialScreen(
    navController: NavController,
    telaInicialViewModel: TelaInicialViewModel
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val showDialogPerfil by telaInicialViewModel.showDialogPerfil.observeAsState(initial = false)
    val showDialogFiltros by telaInicialViewModel.showDialogFiltros.observeAsState(initial = false)
    val onSelected by telaInicialViewModel.onSelected.observeAsState(initial = false)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                // Aqui fica todos os conteudos do Sidebar Menu
                Image(
                    painter = painterResource(id = R.drawable.fiap_logo_sem_fundo),
                    contentDescription = "Logo do Fiapweb",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(100.dp)
                )

                Divider()
                NavigationItem.entries.toTypedArray().take(8).forEach { navigationItem ->
                    NavigationItemView(
                        navigationItem = navigationItem,
                        selected = false,
                        onClick = {

                        }
                    )
                }
            }
        }

    ) {


        // Aqui fica o Conteúdo da tela (recomendo um estudo sobre o Scaffold para melhor entendimento
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { /*TODO Floating button*/ }) {
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit Icon")
                }
            },


            // Se Algum email estiver selecionado esse "Header" substituirá o SearchBar
            topBar = {
                if (onSelected) {
                    SelecionadosHeader(telaInicialViewModel)
                }
            }


        ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {


                val coroutineScope = rememberCoroutineScope()

                Column {

                    //  Barra de pesquisa
                    if (!onSelected) {
                        SearchBarHeader(
                            telaInicialViewModel = telaInicialViewModel,
                            couroutineScope = coroutineScope,
                            drawerState = drawerState
                        )
                    }

                    // Titulo e Filtro em Row
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        // Titulo Do email
                        Text(text = "Todos os emails", modifier = Modifier.padding(16.dp))

                        // Filtro
                        Button(
                            onClick = { telaInicialViewModel.onshowDialogFiltrosChange(true) },
                            modifier = Modifier.padding(end = 16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.onBackground
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.FilterAlt,
                                contentDescription = "Filter Icon",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(text = "Filtros")
                        }

                    }

                    // Dialogs
                    if (showDialogFiltros) {
                        ModalFiltros {
                            telaInicialViewModel.onshowDialogFiltrosChange(false)
                        }
                    }

                    if (showDialogPerfil) {
                        ModalPerfil(onDismissRequest = {
                            telaInicialViewModel.onshowDialogPerfilChange(false)
                        })
                    }

                    val email = Email(
                        id = 1,
                        remetente = "john.doe@example.com",
                        destinatario = "jane.doe@example.com",
                        cc = listOf("cc1@example.com", "cc2@example.com"),
                        bcc = listOf("bcc1@example.com"),
                        subject = "Meeting Reminder",
                        body = "Hi Jane,\n\nJust a reminder about our meeting tomorrow at 10 AM.\n\nBest,\nJohn",
                        attachments = listOf("file1.pdf", "file2.png"),
                        timestamp = LocalDateTime.now(),
                        isRead = false,
                        priority = Priority.HIGH
                    )

//                    var itens by remember {
//                        mutableStateOf(
//                            (1..20).map {
//                                val email = Email(
//                                    id = 0,
//                                    remetente = "john.doe${it}@example.com",
//                                    destinatario = "jane.doe${it}@example.com",
//                                    cc = listOf("cc1@example.com", "cc2@example.com"),
//                                    bcc = listOf("bcc1@example.com"),
//                                    subject = "Meeting Reminder${it}",
//                                    body = "Hi Jane${it},\n\nJust a reminder about our meeting tomorrow at 10 AM.\n\nBest,\nJohn",
//                                    attachments = listOf("file1.pdf", "file2.png"),
//                                    timestamp = LocalDateTime.now(),
//                                    isRead = false,
//                                    priority = Priority.HIGH
//                                )
//                            }
//                        )
//                    }
//                    // Emails
//                    LazyColumn(
//                        modifier = Modifier.fillMaxSize()
//                    ) {
//                        items(itens.size) {
//                            Row {
//
//                            }
//                        }
//                    }

                    EmailView(email = email, onCLick = {}, telaInicialViewModel)

                }
            }
        }
    }
}

