package br.com.fiap.fiapweb.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.fiapweb.R
import br.com.fiap.fiapweb.Repository.EmailRepository
import br.com.fiap.fiapweb.components.EmailView
import br.com.fiap.fiapweb.components.ModalFiltros
import br.com.fiap.fiapweb.components.ModalPerfil
import br.com.fiap.fiapweb.components.NavigationItemView
import br.com.fiap.fiapweb.components.SearchBarHeader
import br.com.fiap.fiapweb.components.SelecionadosHeader
import br.com.fiap.fiapweb.model.Categoria
import br.com.fiap.fiapweb.model.NavigationItem
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel
import kotlinx.coroutines.launch

@Composable
fun TelaInicialScreen(
    navController: NavController,
    telaInicialViewModel: TelaInicialViewModel
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val showDialogPerfil by telaInicialViewModel.showDialogPerfil.observeAsState(initial = false)
    val showDialogFiltros by telaInicialViewModel.showDialogFiltros.observeAsState(initial = false)
    val onSelected by telaInicialViewModel.onSelected.observeAsState(initial = false)
    val qtdEmailSelecionada by telaInicialViewModel.qtdEmailSelecionada.observeAsState(initial = 1)
    val listaDeEmail by telaInicialViewModel.listaCompletaEmailDb.observeAsState(initial = listOf())
    val todosEmailSelecionados by telaInicialViewModel.todosEmailSelecionados.observeAsState(initial = false)
    val categoria by telaInicialViewModel.categoria.observeAsState(initial = Categoria.EMAIL)
    val tituloDaCaixaDeEntrada by telaInicialViewModel.tituloDaCaixaDeEntrada.observeAsState(initial = "Todos os Emails")

    val context = LocalContext.current
    val usuarioRepository = EmailRepository(context)

//    fun inserirEmailsFicticios(repository: EmailRepository) {
//        GlobalScope.launch {
//            for (i in 1..20) {
//                val email = Email(
//                    id = 0, // O ID será gerado automaticamente
//                    remetente = "remetente$i@example.com",
//                    destinatario = "destinatario$i@example.com",
//                    cc = listOf("cc$i@example.com"),
//                    bcc = listOf("bcc$i@example.com"),
//                    subject = "Assunto $i",
//                    body = "Corpo do email $i",
//                    attachments = listOf("anexo$i.pdf"),
//                    timestamp = LocalDateTime.now(),
//                    isRead = false,
//                    isFavorite = false,
//                    priority = Priority.NORMAL,
//                    isSelected = false
//                )
//                repository.salvar(email)
//            }
//        }
//    }
//
//    // Troque para True aqui para colocar 20 itens no banco de dados, mas coloque true rode a aplicação dps retorne pra fase pfv
//    if (true) {
//        inserirEmailsFicticios(usuarioRepository)
//    }

    // Aqui é o Sidebar
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                val coroutineScope = rememberCoroutineScope()

                // Aqui fica todos os conteudos do Sidebar Menu
                Image(
                    painter = painterResource(id = R.drawable.fiap_logo_sem_fundo),
                    contentDescription = "Logo do Fiapweb",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(100.dp)
                )

                Divider()
                // Itens do sidebar
                NavigationItem.entries.toTypedArray().take(8).forEach { navigationItem ->
                    NavigationItemView(
                        navigationItem = navigationItem,
                        selected = false,
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            when (navigationItem.titulo) {

                                "Todas as caixas de entrada" -> {
                                    telaInicialViewModel.onCategoriaChange(Categoria.EMAIL)
                                    telaInicialViewModel.onTituloDaCaixaDeEntradaChange("Todos os Emails")
                                }

                                "Caixas de entrada" -> {
                                    telaInicialViewModel.onCategoriaChange(Categoria.EMAIL)
                                    telaInicialViewModel.onTituloDaCaixaDeEntradaChange("Caixas de entrada")
                                }

                                "Não lidos" -> {
                                    telaInicialViewModel.onCategoriaChange(Categoria.EMAIL)
                                    telaInicialViewModel.onTituloDaCaixaDeEntradaChange("Não lidos")
                                }

                                "Rascunhos" -> {
                                    telaInicialViewModel.onCategoriaChange(Categoria.EMAIL)
                                    telaInicialViewModel.onTituloDaCaixaDeEntradaChange("Rascunhos")
                                }

                                "Enviados" -> {
                                    telaInicialViewModel.onCategoriaChange(Categoria.ENVIADOS)
                                    telaInicialViewModel.onTituloDaCaixaDeEntradaChange("Enviados")
                                }

                                "Lixeira" -> {
                                    telaInicialViewModel.onCategoriaChange(Categoria.LIXEIRA)
                                    telaInicialViewModel.onTituloDaCaixaDeEntradaChange("Lixeira")
                                }

                                "Spam" -> {
                                    telaInicialViewModel.onCategoriaChange(Categoria.SPAM)
                                    telaInicialViewModel.onTituloDaCaixaDeEntradaChange("Spam")
                                }
                            }
                        }
                    )
                }
            }
        }

    ) {

        // Aqui fica o Conteúdo da tela (recomendo um estudo sobre o Scaffold para melhor entendimento
        Scaffold(

            floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate("telaEnvioEmail") }) {
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit Icon")
                }
            },

            // Se Algum email estiver selecionado esse "Header" substituirá o SearchBar
            topBar = {
                if (onSelected) {
                    SelecionadosHeader(
                        telaInicialViewModel,
                        qtdEmailSelecionada,
                        onDeleteClick = {
                            // Aqui é buscado no banco pelos Itens que estão selecionados
                            val listaDosSelecionados =
                                usuarioRepository.listarEmailPorSelecionados()

                            // Para cada item na lista retornada é "deletado" (adiconado um enum diferente) do banco
                            telaInicialViewModel.changeAllEmailToDelete(
                                context,
                                listaDosSelecionados
                            )
                            telaInicialViewModel.changeAllEmailToNotSelected(context)


                            // Aqui para segurança da Lista é atualizada a ListaEmail com os valores novos
                            telaInicialViewModel.onListaCompletaEmailDbChange(
                                telaInicialViewModel.getListaCompletaEmailDb(
                                    context
                                )
                            )

                            telaInicialViewModel.onSelectedChange(false)
                            telaInicialViewModel.onQtdEmailSelecionada(1)

                        },
                        onDraftClick = {},
                        onSelectAllClick = {
                            if (todosEmailSelecionados) {
                                telaInicialViewModel.changeAllEmailToNotSelected(context)
                                telaInicialViewModel.onTodosEmailSelecionadosChange(false)
                            } else {
                                (telaInicialViewModel.changeAllEmailToSelected(context))
                                telaInicialViewModel.onTodosEmailSelecionadosChange(true)
                            }

                        },
                        todosEmailSelecionados
                    )
                }
            }

        ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {

                val coroutineScope = rememberCoroutineScope()

                Column {

                    //  Barra de pesquisa se nenhum item tiver selecionado
                    if (!onSelected) {
                        SearchBarHeader(
                            telaInicialViewModel = telaInicialViewModel,
                            couroutineScope = coroutineScope,
                            drawerState = drawerState
                        )
                    }

                    // Titulo e Filtro em Row (se tiver selecionado ela some)
                    if (!onSelected) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            // Titulo Do email
                            Text(tituloDaCaixaDeEntrada, modifier = Modifier.padding(16.dp))

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

                    // Preenchimento da ListaDeEmail por categoria (categoria é manipulada pelo sidebar)
                    telaInicialViewModel.onListaCompletaEmailDbChange(
                        telaInicialViewModel.getListaEmailPorCategoriaDb(
                            context, categoria
                        )
                    )

                    if (listaDeEmail.isEmpty()) {
                        // Se a Lista estiver vazia:
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Nada em ${categoria.name}")
                        }


                    } else {
                        // Percorrendo a listaDeEmail se a Lista tiver algo
                        LazyColumn {
                            items(listaDeEmail.size) { indexDoEmail ->
                                val listaDeEmailSendoManipulada = listaDeEmail
                                EmailView(
                                    email = listaDeEmail[indexDoEmail],
                                    onCLick = {
                                        if (onSelected) {
                                            listaDeEmailSendoManipulada.mapIndexed { index, email ->
                                                if (index == indexDoEmail) {
                                                    val emailAlterado =
                                                        email.copy(isSelected = !email.isSelected)

                                                    // Alterando Email Selecionado no DB
                                                    telaInicialViewModel.atualizarEmail(
                                                        context,
                                                        emailAlterado
                                                    )

                                                    // Alterando Email Selecionado na ListaDeEmail do Lazy Column
                                                    telaInicialViewModel.onListaCompletaEmailDbChange(
                                                        telaInicialViewModel.getListaEmailPorCategoriaDb(
                                                            context, categoria
                                                        )
                                                    )

                                                    //  Alterando onSelected para alterar o Header da aplicação
                                                    if (telaInicialViewModel.hasSelectedEmail(
                                                            context
                                                        )
                                                    ) {
                                                        telaInicialViewModel.onSelectedChange(true)
                                                    } else {
                                                        telaInicialViewModel.onSelectedChange(false)
                                                    }

                                                    // Alterando o valor da quantidade de email selecionado
                                                    telaInicialViewModel.onQtdEmailSelecionada(
                                                        telaInicialViewModel.countSelectedEmail(
                                                            context
                                                        )
                                                    )

                                                } else email
                                            }
                                        }
                                    },
                                    onLongClick = {
                                        Log.i("FIAP", "TelaInicialScreen: Este botão foi clicado")

                                        listaDeEmailSendoManipulada.mapIndexed { index, email ->
                                            if (index == indexDoEmail) {
                                                val emailAlterado =
                                                    email.copy(isSelected = !email.isSelected)

                                                // Alterando Email Selecionado no DB
                                                telaInicialViewModel.atualizarEmail(
                                                    context,
                                                    emailAlterado
                                                )

                                                // Alterando Email Selecionado na ListaDeEmail do Lazy Column
                                                telaInicialViewModel.onListaCompletaEmailDbChange(
                                                    telaInicialViewModel.getListaEmailPorCategoriaDb(
                                                        context, categoria
                                                    )
                                                )

                                                //  Alterando onSelected para alterar o Header da aplicação
                                                if (telaInicialViewModel.hasSelectedEmail(context)) {
                                                    telaInicialViewModel.onSelectedChange(true)
                                                } else {
                                                    telaInicialViewModel.onSelectedChange(false)
                                                }

                                            } else email
                                        }
                                    },
                                    telaInicialViewModel = telaInicialViewModel
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}