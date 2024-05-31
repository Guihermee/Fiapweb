package br.com.fiap.fiapweb.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.fiapweb.R
import br.com.fiap.fiapweb.Repository.HistoricoDeBuscaRespository
import br.com.fiap.fiapweb.components.ModalDialogPerfil
import br.com.fiap.fiapweb.components.NavigationItemView
import br.com.fiap.fiapweb.components.SearchBarHeader
import br.com.fiap.fiapweb.model.HistoricoDeBusca
import br.com.fiap.fiapweb.model.NavigationItem
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaInicialScreen(
    navController: NavController,
    telaInicialViewModel: TelaInicialViewModel
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // Aqui fica todos os conteudos do Sidebar Menu

//                Text("Drawer title", modifier = Modifier.padding(16.dp))
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

        ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {

                val coroutineScope = rememberCoroutineScope()

                Column {

                    //  Barra de pesquisa
                    SearchBarHeader(
                        telaInicialViewModel = telaInicialViewModel,
                        couroutineScope = coroutineScope,
                        drawerState = drawerState
                    )

                    // Titulo e Filtro em Row
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        // Titulo Do email
                        Text(text = "Todos os emails", modifier = Modifier.padding(16.dp))

                        // Filtro do email
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.padding(end = 16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.secondary
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

                    // Emails



                }
            }
        }
    }
}

