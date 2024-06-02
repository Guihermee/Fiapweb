package br.com.fiap.fiapweb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.fiapweb.model.Email

@Dao
interface EmailDAO {

    @Insert
    fun salvar(email: Email): Long

    @Delete
    fun deletar(email: Email): Int

    @Update
    fun atualizar(email: Email): Int

    @Query("SELECT * FROM tbl_email ORDER BY timestamp")
    fun listarHistorico(): List<Email>

}