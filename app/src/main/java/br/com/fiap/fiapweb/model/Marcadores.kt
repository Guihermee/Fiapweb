package br.com.fiap.fiapweb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_marcadores")
data class Marcadores(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val nome: String = ""
)
