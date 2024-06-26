package br.com.fiap.fiapweb.Repository

import android.content.Context
import br.com.fiap.fiapweb.dao.UsuarioDb
import br.com.fiap.fiapweb.model.Categoria
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
        return db.listarEmail()
    }

    fun listarEmailPorSelecionados(): List<Email> {
        return db.listarEmailPorSelecionados()
    }

    fun listarEmailPorCategoria(categoria: Categoria): List<Email> {
        return db.listarEmailPorCategoria(categoria)
    }

    fun listarEmailPorPesquisa(pesquisa: String): List<Email> {
        val pesquisaConcatenada = "${pesquisa}%"
        return db.listarEmailPorPesquisa(pesquisaConcatenada)
    }

    fun listarEmailPorFavorito(): List<Email> {
        return db.listarEmailPorFavoritos()
    }

    fun listarEmailsFavoritos(): List<Email> {
        return db.buscarEmailsFavoritos()
    }

    fun listarEmailsPeloMarcador(id: Long): List<Email> {
        return db.buscarEmailPeloMarcador(id)
    }

}