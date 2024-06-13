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
import androidx.compose.material.icons.outlined.MarkEmailRead
import androidx.compose.material.icons.outlined.MarkEmailUnread
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.fiapweb.R
import br.com.fiap.fiapweb.Repository.EmailRepository
import br.com.fiap.fiapweb.Repository.HistoricoDeBuscaRespository
import br.com.fiap.fiapweb.Repository.NavigationItemRepository
import br.com.fiap.fiapweb.components.EmailView
import br.com.fiap.fiapweb.components.ModalFiltros
import br.com.fiap.fiapweb.components.ModalPerfil
import br.com.fiap.fiapweb.components.NavigationItemView
import br.com.fiap.fiapweb.components.SearchBarHeader
import br.com.fiap.fiapweb.components.SelecionadosHeader
import br.com.fiap.fiapweb.model.Categoria
import br.com.fiap.fiapweb.model.Email
import br.com.fiap.fiapweb.model.HistoricoDeBusca
import br.com.fiap.fiapweb.model.Priority
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime

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
    val selectedItemIndex by telaInicialViewModel.selectedItemIndex.observeAsState(initial = 0)
    val listNavigationItem = NavigationItemRepository().getNavigationItemList()
    val iconDraftSelected by telaInicialViewModel.iconDraftSelected.observeAsState(initial = false)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    val context = LocalContext.current
    val historicoRepository = HistoricoDeBuscaRespository(context)
    val usuarioRepository = EmailRepository(context)

    fun inserirEmailsFicticios(repository: EmailRepository) {
        GlobalScope.launch {
            for (i in 1..20) {
                val email = Email(
                    id = 0, // O ID será gerado automaticamente
                    remetente = "remetente$i@example.com",
                    destinatario = "destinatario$i@example.com",
                    cc = listOf("cc$i@example.com"),
                    bcc = listOf("bcc$i@example.com"),
                    subject = "Assunto $i",
                    body = "Corpo do email $i",
                    attachments = listOf("anexo$i.pdf"),
                    timestamp = LocalDateTime.now(),
                    isRead = i == 1,
                    isFavorite = false,
                    priority = Priority.NORMAL,
                    isSelected = false
                )
                repository.salvar(email)
            }
        }
    }

    // descomente, rode a aplicação e comente novamente, não rode esse comando abaixo 2 vezes (ele gera 20 email no banco de dados para testes)
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
                listNavigationItem.forEachIndexed { index, navigationItem ->
                    NavigationItemView(
                        navigationItem = navigationItem,
                        selected = index == selectedItemIndex,
                        onClick = {
                            telaInicialViewModel.onSelectedItemIndex(index)
                            coroutineScope.launch { drawerState.close() }
                            when (navigationItem.titulo) {
                                "Todas as caixas de entrada" -> {
                                    telaInicialViewModel.onCategoriaChange(Categoria.EMAIL)
                                    telaInicialViewModel.atualizarListaComPesquisaNoDb(context, categoria)
                                    telaInicialViewModel.onTituloDaCaixaDeEntradaChange("Todos os Emails")
                                }

                                "Caixas de entrada" -> {
                                    telaInicialViewModel.onCategoriaChange(Categoria.EMAIL)
                                    telaInicialViewModel.atualizarListaComPesquisaNoDb(context, categoria)
                                    telaInicialViewModel.onTituloDaCaixaDeEntradaChange("Caixas de entrada")
                                }

                                "Favoritos" -> {
                                    telaInicialViewModel.onCategoriaChange(Categoria.FAVORITOS)
                                    telaInicialViewModel.atualizarListaComPesquisaNoDb(context, categoria)
                                    telaInicialViewModel.onTituloDaCaixaDeEntradaChange("Favoritos")
                                }

                                "Não lidos" -> {
                                    telaInicialViewModel.onCategoriaChange(Categoria.EMAIL)
                                    telaInicialViewModel.atualizarListaComPesquisaNoDb(context, categoria)
                                    telaInicialViewModel.onTituloDaCaixaDeEntradaChange("Não lidos")
                                }

                                "Rascunhos" -> {
                                    telaInicialViewModel.onCategoriaChange(Categoria.EMAIL)
                                    telaInicialViewModel.atualizarListaComPesquisaNoDb(context, categoria)
                                    telaInicialViewModel.onTituloDaCaixaDeEntradaChange("Rascunhos")
                                }

                                "Enviados" -> {
                                    telaInicialViewModel.onCategoriaChange(Categoria.ENVIADOS)
                                    telaInicialViewModel.atualizarListaComPesquisaNoDb(context, categoria)
                                    telaInicialViewModel.onTituloDaCaixaDeEntradaChange("Enviados")
                                }

                                "Lixeira" -> {
                                    telaInicialViewModel.onCategoriaChange(Categoria.LIXEIRA)
                                    telaInicialViewModel.atualizarListaComPesquisaNoDb(context, categoria)
                                    telaInicialViewModel.onTituloDaCaixaDeEntradaChange("Lixeira")
                                }

                                "Spam" -> {
                                    telaInicialViewModel.onCategoriaChange(Categoria.SPAM)
                                    telaInicialViewModel.atualizarListaComPesquisaNoDb(context, categoria)
                                    telaInicialViewModel.onTituloDaCaixaDeEntradaChange("Spam")
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { // Aqui fica o Conteúdo da tela (recomendo um estudo sobre o Scaffold para melhor entendimento)
        Scaffold(

            floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate("telaEnvioEmail") }) {
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit Icon")
                }
            },

            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
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


                            // Mudanda a categoria dos emails selecionados
                            telaInicialViewModel.changeListaEmailCategoria(
                                context,
                                listaDosSelecionados,
                                Categoria.LIXEIRA
                            )
                            telaInicialViewModel.changeAllEmailSelectTo(context, false)

                            // Aqui para segurança da Lista é atualizada a ListaEmail com os valores novos
                            telaInicialViewModel.atualizarListaComPesquisaNoDb(context, categoria)

                            telaInicialViewModel.onSelectedChange(false)
                            telaInicialViewModel.onQtdEmailSelecionada(1)

                            //  Notificação para dizer que foi excluido um email
                            scope.launch {
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = "Email foi para Lixeira!",
                                        actionLabel = "Desfazer",
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        // Mudanda a categoria dos emails selecionados
                                        telaInicialViewModel.changeListaEmailCategoria(
                                            context,
                                            listaDosSelecionados,
                                            Categoria.EMAIL
                                        )
                                        // Aqui para segurança da Lista é atualizada a ListaEmail com os valores novos
                                        telaInicialViewModel.atualizarListaComPesquisaNoDb(context, categoria)
                                    }

                                    SnackbarResult.Dismissed -> {
                                        /* Handle snackbar dismissed */
                                    }
                                }
                            }
                        },
                        IconDraft = if (iconDraftSelected) Icons.Outlined.MarkEmailUnread else Icons.Outlined.MarkEmailRead,
                        onDraftClick = {
                            val listaDosSelecionados =
                                usuarioRepository.listarEmailPorSelecionados()

                            // Mudando o IsRead do email
                            telaInicialViewModel.changeAllEmailToOrNotRead(
                                context,
                                listaDosSelecionados,
                                !iconDraftSelected
                            )
                            telaInicialViewModel.onIconDraftSelectedChange(!iconDraftSelected)

                            // Aqui para segurança da Lista é atualizada a ListaEmail com os valores novos
                            telaInicialViewModel.atualizarListaComPesquisaNoDb(context, categoria)

                        },
                        onSelectAllClick = {
                            if (todosEmailSelecionados) {
                                telaInicialViewModel.changeAllEmailSelectTo(context, false)
                                telaInicialViewModel.onTodosEmailSelecionadosChange(false)
                                // Alterando o valor da quantidade de email selecionado
                                telaInicialViewModel.onQtdEmailSelecionada(0)

                                // Aqui para segurança da Lista é atualizada a ListaEmail com os valores novos
                                telaInicialViewModel.atualizarListaComPesquisaNoDb(context, categoria)
                            } else {
                                (telaInicialViewModel.changeAllEmailSelectTo(context, true))
                                telaInicialViewModel.onTodosEmailSelecionadosChange(true)
                                // Alterando o valor da quantidade de email selecionado
                                telaInicialViewModel.onQtdEmailSelecionada(
                                    telaInicialViewModel.countSelectedEmail(
                                        context
                                    )
                                )
                                // Aqui para segurança da Lista é atualizada a ListaEmail com os valores novos
                                telaInicialViewModel.atualizarListaComPesquisaNoDb(context, categoria)
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
                            drawerState = drawerState,
                            onSearch = {
                                val listaDoHistorico = historicoRepository.listarHistorico().map { it.pesquisa }
                                var textFieldValue = telaInicialViewModel.textField.value!!

                                if (textFieldValue in listaDoHistorico) {
                                    val pesquisaRepetida = historicoRepository.buscarHistoricoPorPesquisa(textFieldValue)
                                    historicoRepository.deletar(pesquisaRepetida)
                                }

                                historicoRepository.salvar(HistoricoDeBusca(pesquisa = textFieldValue))
                                telaInicialViewModel.onListaHistoricoChange(historicoRepository.listarHistorico())
                                telaInicialViewModel.setIsSearchingToFalse()
                                telaInicialViewModel.onTodosEmailSelecionadosChange(false)

                                val pesquisaDoUsuario = usuarioRepository.listarEmailPorPesquisa(textFieldValue)
                                telaInicialViewModel.onListaCompletaEmailDbChange(pesquisaDoUsuario)
                            }
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
                        ModalFiltros(
                            onDismissRequest = {
                                telaInicialViewModel.onshowDialogFiltrosChange(false)
                            },
                            onAplicarRequest = {}
                        )
                    }

                    if (showDialogPerfil) {
                        ModalPerfil(onDismissRequest = {
                            telaInicialViewModel.onshowDialogPerfilChange(false)
                        })
                    }

                    fun primeiraInicializacao() {
                        telaInicialViewModel.onListaCompletaEmailDbChange(
                            telaInicialViewModel.getListaEmailPorCategoriaDb(
                                context, categoria
                            )
                        )
                    }

                    // Preenchimento da ListaDeEmail por categoria (categoria é manipulada pelo sidebar)
                    LaunchedEffect(Unit) {
                        primeiraInicializacao()
                    }

                    // Se a Lista estiver vazia:
                    if (listaDeEmail.isEmpty()) {
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
                                                    telaInicialViewModel.atualizarListaComPesquisaNoDb(context, categoria)

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

                                        // Quando clica no email?....

                                    },
                                    onLongClick = {
                                        listaDeEmailSendoManipulada.mapIndexed { index, email ->
                                            if (index == indexDoEmail) {
                                                val emailAlterado =
                                                    email.copy(isSelected = !email.isSelected)

                                                // alterando iconDraftSelected
                                                telaInicialViewModel.onIconDraftSelectedChange(email.isRead)

                                                // Alterando Email Selecionado no DB
                                                telaInicialViewModel.atualizarEmail(
                                                    context,
                                                    emailAlterado
                                                )

                                                // Alterando Email Selecionado na ListaDeEmail do Lazy Column
                                                telaInicialViewModel.atualizarListaComPesquisaNoDb(context, categoria)

                                                //  Alterando onSelected para alterar o Header da aplicação
                                                if (telaInicialViewModel.hasSelectedEmail(context)) {
                                                    telaInicialViewModel.onSelectedChange(true)
                                                } else {
                                                    telaInicialViewModel.onSelectedChange(false)
                                                }

                                            } else email
                                        }
                                    },
                                    onEstrelaClick = {
                                        listaDeEmailSendoManipulada.mapIndexed { index, email ->
                                            if (index == indexDoEmail) {
                                                val emailAlterado = email.copy(
                                                    isFavorite = !email.isFavorite
                                                )

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
                                            } else email
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}