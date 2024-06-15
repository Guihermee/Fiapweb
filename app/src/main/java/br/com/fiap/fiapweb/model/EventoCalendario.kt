package br.com.fiap.fiapweb.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tbl_eventos")
data class EventoCalendario(

    @PrimaryKey (autoGenerate = true) val id: String,
    val titulo: String,
    val descricao: String,
    val dataInicio: Date,
    val dataFim: Date,
    val synced: Boolean // Flag to check if the event is synced with Google Calendar
)


