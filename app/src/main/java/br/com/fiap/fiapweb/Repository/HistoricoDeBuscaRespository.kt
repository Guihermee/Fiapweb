package br.com.fiap.fiapweb.Repository

import android.content.Context
import br.com.fiap.fiapweb.dao.UsuarioDb
import br.com.fiap.fiapweb.model.HistoricoDeBusca

class HistoricoDeBuscaRespository(context: Context) {
    var db = UsuarioDb.getDatabase(context).historicoDeBuscaDAO()

    fun salvar(historicoDeBusca: HistoricoDeBusca): Long {
        return db.salvar(historicoDeBusca)
    }

    fun deletar(historicoDeBusca: HistoricoDeBusca): Int {
        return db.deletar(historicoDeBusca)
    }

    fun listarHistorico(): List<HistoricoDeBusca> {
        return db.listarHistorico()
    }

}