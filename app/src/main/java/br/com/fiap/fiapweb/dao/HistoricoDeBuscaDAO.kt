package br.com.fiap.fiapweb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.fiap.fiapweb.model.HistoricoDeBusca

@Dao
interface HistoricoDeBuscaDAO {

    @Insert
    fun salvar(historicoDeBusca: HistoricoDeBusca): Long

    @Delete
    fun deletar(historicoDeBusca: HistoricoDeBusca): Int

    @Query("SELECT * FROM tbl_historico_de_busca ORDER BY id DESC")
    fun listarHistorico(): List<HistoricoDeBusca>

}