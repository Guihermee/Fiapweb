package br.com.fiap.fiapweb.utils

import br.com.fiap.fiapweb.model.Email

fun converterParaListaDeEmails(listaDeAny: List<Any>): List<Email> {
    return listaDeAny.mapNotNull { it as? Email }
}
