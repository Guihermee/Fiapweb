package br.com.fiap.fiapweb.model


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDateTime

@Entity(
    tableName = "tbl_email",
    foreignKeys = [ForeignKey(
        entity = Marcadores::class,
        parentColumns = ["id"],
        childColumns = ["marcador_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["marcador_id"])]
)
data class Email (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val remetente: String,
    val destinatario: String,
    val cc: String,
    //List<String> = emptyList(), // Lista de endereços de email para cópia carbono
    val bcc: String,
    //List<String> = emptyList(), // Lista de endereços de email para cópia carbono oculta
    val subject: String,
    val body: String,
    val attachments: List<String> = emptyList(),
    val timestamp: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "is_read") val isRead: Boolean = false,
    @ColumnInfo(name = "is_favorito") val isFavorite: Boolean = false,
    val priority: Priority = Priority.NORMAL,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean = false,
    val categoria: Categoria = Categoria.EMAIL,
    @ColumnInfo(name = "marcador_id") val marcadorId: Long
)

data class EmailWithMarcadores(
    @Embedded val email: Email,
    @Relation(
        parentColumn = "id",
        entityColumn = "marcador_id"
    )
    val marcadores: Marcadores
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