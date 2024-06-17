package br.com.fiap.fiapweb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.fiapweb.model.Marcadores

@Dao
interface MarcadoresDAO  {

    @Insert
    fun salvar(marcadores: Marcadores): Long

    @Update
    fun atualizar(marcadores: Marcadores): Int

    @Delete
    fun deletar(marcadores: Marcadores): Int

    @Query("SELECT * FROM tbl_marcadores")
    fun listarMarcadores(): List<Marcadores>

    @Query("SELECT * FROM tbl_marcadores WHERE id = :id")
    fun listarMarcadoresPorId(id: Long): Marcadores

}