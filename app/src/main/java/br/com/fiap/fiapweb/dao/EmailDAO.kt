package br.com.fiap.fiapweb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.fiapweb.model.Categoria
import br.com.fiap.fiapweb.model.Email

@Dao
interface EmailDAO {

    @Insert
    fun salvar(email: Email): Long

    @Delete
    fun deletar(email: Email): Int

    @Update
    fun atualizar(email: Email): Int

    @Query("SELECT * FROM tbl_email ORDER BY timestamp DESC")
    fun listarEmail(): List<Email>

    @Query("SELECT * FROM tbl_email WHERE is_selected = 1")
    fun listarEmailPorSelecionados(): List<Email>

    @Query("SELECT * FROM tbl_email WHERE categoria = :categoria ORDER BY timestamp DESC")
    fun listarEmailPorCategoria(categoria: Categoria): List<Email>

    @Query("SELECT * FROM tbl_email WHERE remetente LIKE :pesquisa ORDER BY timestamp DESC")
    fun listarEmailPorPesquisa(pesquisa: String): List<Email>

    @Query("SELECT * FROM tbl_email WHERE is_favorito = 1 ORDER BY timestamp DESC")
    fun listarEmailPorFavoritos(): List<Email>

    @Query("SELECT * FROM tbl_email WHERE is_favorito = true")
    fun buscarEmailsFavoritos(): List<Email>

    @Query("SELECT * FROM tbl_email WHERE marcador_id = :id")
    fun buscarEmailPeloMarcador(id: Long): List<Email>

}