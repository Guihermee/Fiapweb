package br.com.fiap.fiapweb.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "tbl_email")
data class Email(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val remetente: String,
    val destinatario: String,
    val cc: List<String> = emptyList(), // Lista de endereços de email para cópia carbono
    val bcc: List<String> = emptyList(), // Lista de endereços de email para cópia carbono oculta
    val subject: String,
    val body: String,
    val attachments: List<String> = emptyList(),
    val timestamp: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "is_read") val isRead: Boolean = false,
    @ColumnInfo(name = "is_favorito") val isFavorite: Boolean = false,
    val priority: Priority = Priority.NORMAL,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean = false,
    val categoria: Categoria = Categoria.EMAIL
)

enum class Priority {
    HIGH,
    NORMAL,
    LOW
}

enum class Categoria {
    EMAIL,
    LIXEIRA,
    SPAM,
    ENVIADOS,
    FAVORITOS
}