package br.com.fiap.fiapweb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.fiapweb.screens.TelaAdicionarEventoScreen
import br.com.fiap.fiapweb.screens.TelaEnvioDeEmailScreen
import br.com.fiap.fiapweb.screens.TelaInicialScreen
import br.com.fiap.fiapweb.screens.TelaLerEmailScreen
import br.com.fiap.fiapweb.ui.theme.FiapwebTheme
import br.com.fiap.fiapweb.viewModel.AdicionarEventoViewModel
import br.com.fiap.fiapweb.viewModel.EnvioDeEmailViewModel
import br.com.fiap.fiapweb.viewModel.ModalOpenAIViewModel
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel
import br.com.fiap.fiapweb.viewModel.TelaLerEmailViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FiapwebTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "telaInicial") {

                        // Tela Inicial
                        composable(route = "telaInicial") {
                            TelaInicialScreen(
                                navController,
                                TelaInicialViewModel()
                            )
                        }

                        // Tela envio de email
                        composable(route = "telaEnvioEmail") {
                            TelaEnvioDeEmailScreen(
                                navController,
                                EnvioDeEmailViewModel(),
                                ModalOpenAIViewModel()
                            )
                        }

                        //Tela leitura de email
                        composable(
                            route = "telaLeituraEmail?email={email}",
                            arguments = listOf(
                                navArgument(name = "email") {
                                    defaultValue = ""
                                }
                            )
                        ) {
                            val email = it.arguments?.getString("email")!!
                            TelaLerEmailScreen(
                                navController,
                                TelaLerEmailViewModel(),
                                TelaInicialViewModel(),
                                email
                            )
                        }

                        //Tela adicionar evento
                        composable(route = "telaAdicionarEvento") {
                            TelaAdicionarEventoScreen(
                                navController,
                                AdicionarEventoViewModel(LocalContext.current)
                            )
                        }
                    }
                }
            }
        }
    }
}