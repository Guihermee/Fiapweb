package br.com.fiap.fiapweb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_historico_de_busca")
data class HistoricoDeBusca(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pesquisa: String

)
