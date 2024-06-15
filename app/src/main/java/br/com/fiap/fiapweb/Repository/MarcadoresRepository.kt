package br.com.fiap.fiapweb.Repository

import android.content.Context
import br.com.fiap.fiapweb.dao.UsuarioDb
import br.com.fiap.fiapweb.model.Marcadores

class MarcadoresRepository(context: Context) {
    val db = UsuarioDb.getDatabase(context).marcadoresDAO()

    fun salvar(marcadores: Marcadores): Long {
        return db.salvar(marcadores)
    }

    fun atualizar(marcadores: Marcadores): Int {
        return db.atualizar(marcadores)
    }

    fun deletar(marcadores: Marcadores): Int {
        return db.deletar(marcadores)
    }

    fun listar(): List<Marcadores> {
        return db.listarMarcadores()
    }

}