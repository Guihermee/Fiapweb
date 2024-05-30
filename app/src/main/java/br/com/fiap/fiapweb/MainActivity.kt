package br.com.fiap.fiapweb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.fiapweb.screens.TelaInicialScreen
import br.com.fiap.fiapweb.ui.theme.FiapwebTheme
import br.com.fiap.fiapweb.viewModel.TelaInicialViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FiapwebTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    NavHost(navController = navController, startDestination = "telaInicial" ) {
                        composable(route = "telaInicial") {
                            TelaInicialScreen(navController, TelaInicialViewModel())
                        }
                    }

                }
            }
        }
    }
}
