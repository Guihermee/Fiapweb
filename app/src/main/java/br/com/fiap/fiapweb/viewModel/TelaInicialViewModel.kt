package br.com.fiap.fiapweb.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import br.com.fiap.fiapweb.Repository.EmailRepository
import br.com.fiap.fiapweb.model.Email
import br.com.fiap.fiapweb.model.HistoricoDeBusca
import br.com.fiap.fiapweb.utils.converterParaListaDeEmails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TelaInicialViewModel : ViewModel() {

    // Manipulação da Lista Completa do Email do Banco da dados do Usuário e outros métodos
    private val _listaCompletaEmailDb = MutableLiveData<List<Email>>()
    val listaCompletaEmailDb: LiveData<List<Email>> = _listaCompletaEmailDb

    fun onListaCompletaEmailDbChange(novaLista: List<Email>) {
        _listaCompletaEmailDb.value = novaLista
    }

    fun onListaCompletaEmailDbChangeNull(novaLista: List<Email>) {
        _listaCompletaEmailDb.value = novaLista
    }

    fun getListaCompletaEmailDb(context: Context): List<Email> {
        val usuarioRepository = EmailRepository(context)
        return usuarioRepository.db.listarHistorico()
    }

    fun onIsSelectedEmailDb(context: Context, emailASerAtualizado: Email) {
        val usuarioRepository = EmailRepository(context)
        usuarioRepository.db.atualizar(emailASerAtualizado)
    }

    //showDialogFiltros
    private val _showDialogFiltros = MutableLiveData<Boolean>()
    val showDialogFiltros: LiveData<Boolean> = _showDialogFiltros

    fun onshowDialogFiltrosChange(showDialogFiltrosNewValue: Boolean) {
        _showDialogFiltros.value = showDialogFiltrosNewValue
    }

    //  qtdEmailSelecionada
    private val _qtdEmailSelecionada = MutableLiveData<Int>()
    val qtdEmailSelecionada: LiveData<Int> = _qtdEmailSelecionada

    fun onQtdEmailSelecionada(novaQtd: Int) {
        _qtdEmailSelecionada.value = novaQtd
    }

    // onSelected
    private val _onSelected = MutableLiveData<Boolean>()
    val onSelected: LiveData<Boolean> = _onSelected

    fun onSelectedChange(onSelectedNewValue: Boolean) {
        _onSelected.value = onSelectedNewValue
    }

    fun hasSelectedEmail(context: Context): Boolean {
        return getListaCompletaEmailDb(context).any {it.isSelected}
    }

    fun countSelectedEmail(context: Context): Int {
        var quatidade = getListaCompletaEmailDb(context).count() {it.isSelected}

        if (quatidade == 0) {quatidade = 1}

        return quatidade
    }

    fun changeAllEmailToSelected(context: Context) {
        var lista = mutableListOf<Email>()

        _listaCompletaEmailDb.value?.map {

            val emailAlterado = it.copy(isSelected = true)
            onIsSelectedEmailDb(context, emailAlterado)
            lista.add(emailAlterado)
        }
        onListaCompletaEmailDbChange(lista)
        _qtdEmailSelecionada.value = getListaCompletaEmailDb(context).count()

    }

    fun changeAllEmailToNotSelected(context: Context) {
        var lista = mutableListOf<Email>()
        _listaCompletaEmailDb.value?.map {
            val emailAlterado = it.copy(isSelected = false)
            onIsSelectedEmailDb(context, emailAlterado)
            lista.add(emailAlterado)
        }
        onListaCompletaEmailDbChange(lista)
        _qtdEmailSelecionada.value = 0
    }

    // showDialogPerfil
    private val _showDialogPerfil = MutableLiveData<Boolean>()
    val showDialogPerfil: LiveData<Boolean> = _showDialogPerfil

    fun onshowDialogPerfilChange(showDialogPerfilNewValue: Boolean) {
        _showDialogPerfil.value = showDialogPerfilNewValue
    }

    // TextField
    private val _textField = MutableLiveData<String>()
    val textField: LiveData<String> = _textField

    fun onTextFieldChange(textFieldCampo: String) {
        _textField.value = textFieldCampo
    }

    fun clearTextField() {
        _textField.value = ""
    }


    // isSearching
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onTextFieldChange("")
        }
    }

    fun setIsSearchingToFalse() {
        _isSearching.value = false
    }

    // Historico
    private val _listaHistorico = MutableLiveData<List<HistoricoDeBusca>>()
    val listaHistorico: LiveData<List<HistoricoDeBusca>> = _listaHistorico

    fun onListaHistoricoChange(listaNovaHistorico: List<HistoricoDeBusca>) {
        _listaHistorico.value = listaNovaHistorico
    }

    // todosEmailSelecionados
    private val _todosEmailSelecionados = MutableLiveData<Boolean> ()
    val todosEmailSelecionados: LiveData<Boolean> = _todosEmailSelecionados

    fun onTodosEmailSelecionadosChange(novoValor: Boolean) {
        _todosEmailSelecionados.value = novoValor
    }

}