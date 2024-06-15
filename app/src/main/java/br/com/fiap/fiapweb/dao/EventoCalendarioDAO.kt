package br.com.fiap.fiapweb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.fiapweb.model.EventoCalendario

@Dao
interface EventoCalendarioDAO {

    @Query("SELECT * FROM tbl_eventos")
    fun listarTodosEventos(): List<EventoCalendario>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirEvento(eventoCalendario: EventoCalendario): Long

    @Update
    suspend fun atualizarEvento(eventoCalendario: EventoCalendario): Int

    @Delete
    suspend fun apagarEvento(eventoCalendario: EventoCalendario): Int

}