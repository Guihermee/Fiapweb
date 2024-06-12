package br.com.fiap.fiapweb.Repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Drafts
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.AccessAlarms
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Settings
import br.com.fiap.fiapweb.model.NavigationItem

class NavigationItemRepository {

    fun getNavigationItemList(): List<NavigationItem> {
        return listOf(
            NavigationItem(
                icone = Icons.Default.AllInbox,
                titulo = "Todas as caixas de entrada"
            ),
            NavigationItem(
                icone = Icons.Default.AllInbox,
                titulo = "Caixas de entrada"
            ),
            NavigationItem(
                icone = Icons.Default.StarBorder,
                titulo = "Favoritos"
            ),
            NavigationItem(
                icone = Icons.Default.Drafts,
                titulo = "Não lidos"
            ),
            NavigationItem(
                icone = Icons.AutoMirrored.Outlined.InsertDriveFile,
                titulo = "Rascunhos"
            ),
            NavigationItem(
                icone = Icons.AutoMirrored.Outlined.Send,
                titulo = "Enviados"
            ),
            NavigationItem(
                icone = Icons.Outlined.Delete,
                titulo = "Lixeira"
            ),
            NavigationItem(
                icone = Icons.Outlined.AccessAlarms,
                titulo = "Spam"
            ),
            NavigationItem(
                icone = Icons.Outlined.Settings,
                titulo = "Configurações"
            )
        )
    }
}