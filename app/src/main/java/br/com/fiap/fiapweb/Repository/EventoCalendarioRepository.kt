package br.com.fiap.fiapweb.Repository

import br.com.fiap.fiapweb.dao.UsuarioDb
import android.content.Context
import br.com.fiap.fiapweb.model.EventoCalendario


class EventoCalendarioRepository (context: Context){

    val db = UsuarioDb.getDatabase(context).eventoCalendarioDAO()

    suspend fun inserirEvento (eventoCalendario: EventoCalendario) : Long{
        return db.inserirEvento(eventoCalendario = eventoCalendario)
    }

    suspend fun atualizarEvento (eventoCalendario: EventoCalendario): Int{
        return db.atualizarEvento(eventoCalendario = eventoCalendario)
    }

    suspend fun apagarEvento (eventoCalendario: EventoCalendario): Int{
        return db.apagarEvento(eventoCalendario= eventoCalendario)
    }

    suspend fun listarEventos(): List<EventoCalendario>{
        return db.listarTodosEventos()
    }
}