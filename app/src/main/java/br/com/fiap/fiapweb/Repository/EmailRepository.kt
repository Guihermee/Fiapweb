package br.com.fiap.fiapweb.Repository

import android.content.Context
import br.com.fiap.fiapweb.dao.UsuarioDb
import br.com.fiap.fiapweb.model.Email

class EmailRepository(context: Context) {
    val db = UsuarioDb.getDatabase(context).emailDAO()

    fun salvar(email: Email): Long {
        return db.salvar(email)
    }

    fun deletar(email: Email): Int {
        return db.deletar(email)
    }

    fun atualizar(email: Email): Int {
        return db.atualizar(email)
    }

    fun listarEmail(): List<Email> {
        return db.listarHistorico()
    }

}