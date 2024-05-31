package br.com.fiap.fiapweb.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Drafts
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.AccessAlarms
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.InsertDriveFile
import androidx.compose.material.icons.outlined.RestoreFromTrash
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavigationItem(
    val titulo: String,
    val icone: ImageVector
) {
    AllInbox(
        icone = Icons.Default.AllInbox,
        titulo = "Todas as caixas de entrada"
    ),
    Inbox(
        icone = Icons.Default.AllInbox,
        titulo = "Caixas de entrada"
    ),
    Favoritos(
        icone = Icons.Default.StarBorder,
        titulo = "Favoritos"
    ),
    NotRead(
        icone = Icons.Default.Drafts,
        titulo = "Não lidos"
    ),
    Racunhos(
        icone = Icons.AutoMirrored.Outlined.InsertDriveFile,
        titulo = "Rascunhos"
    ),
    Enviados(
        icone = Icons.AutoMirrored.Outlined.Send,
        titulo = "Enviados"
    ),
    Lixeira(
        icone = Icons.Outlined.Delete,
        titulo = "Lixeira"
    ),
    Spam(
        icone = Icons.Outlined.AccessAlarms,
        titulo = "Spam"
    ),
    Configuracoes(
        icone = Icons.Outlined.Settings,
        titulo = "Configurações"
    )

}