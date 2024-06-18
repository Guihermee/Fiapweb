package br.com.fiap.fiapweb.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.fiap.fiapweb.Repository.EmailRepository
import br.com.fiap.fiapweb.model.Categoria
import br.com.fiap.fiapweb.model.Email
import br.com.fiap.fiapweb.model.HistoricoDeBusca
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TelaInicialViewModel : ViewModel() {

    // Manipulação da Lista Completa do Email do Banco da dados do Usuário e outros métodos
    private val _listaCompletaEmailDb = MutableLiveData<List<Email>>()
    val listaCompletaEmailDb: LiveData<List<Email>> = _listaCompletaEmailDb

    fun onListaCompletaEmailDbChange(novaLista: List<Email>) {
        _listaCompletaEmailDb.value = novaLista
    }

    fun getListaCompletaEmailDb(context: Context): List<Email> {
        val usuarioRepository = EmailRepository(context)
        return usuarioRepository.listarEmail()
    }

    fun atualizarListaEmailPorCategoria(
        context: Context,
        categoria: Categoria,
        onLongClick: Boolean = false
    ) {
        if (onLongClick) {
            onListaCompletaEmailDbChange(getListaEmailPorCategoriaDb(context, categoria))
        } else if (_categoria.value == Categoria.FAVORITOS) {
            val usuarioRepository = EmailRepository(context)
            onListaCompletaEmailDbChange(usuarioRepository.listarEmailPorFavorito())
        } else {
            onListaCompletaEmailDbChange(getListaEmailPorCategoriaDb(context, categoria))
        }
    }

    fun getListaEmailPorCategoriaDb(context: Context, categoria: Categoria): List<Email> {
        val usuarioRepository = EmailRepository(context)
        return usuarioRepository.listarEmailPorCategoria(categoria)
    }

    fun atualizarEmail(context: Context, emailASerAtualizado: Email) {
        val usuarioRepository = EmailRepository(context)
        usuarioRepository.atualizar(emailASerAtualizado)
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
        return getListaCompletaEmailDb(context).any { it.isSelected }
    }

    fun countSelectedEmail(context: Context): Int {
        var quatidade = getListaCompletaEmailDb(context).count { it.isSelected }

        if (quatidade == 0) {
            quatidade = 1
        }

        return quatidade
    }

    fun changeAllEmailSelectTo(context: Context, isSelected: Boolean) {
        val lista = mutableListOf<Email>()

        _listaCompletaEmailDb.value?.map {

            val emailAlterado = it.copy(isSelected = isSelected)
            atualizarEmail(context, emailAlterado)
            lista.add(emailAlterado)
        }
        _qtdEmailSelecionada.value = getListaCompletaEmailDb(context).count()
    }

    fun changeListaEmailCategoria(
        context: Context,
        listaASerDeletada: List<Email>,
        categoria: Categoria
    ) {
        for (deletado in listaASerDeletada) {

            val listaASerPercorrida: List<Email> = if (categoria == Categoria.EMAIL) {
                getListaCompletaEmailDb(context)
            } else {
                _listaCompletaEmailDb.value!!
            }

            listaASerPercorrida.map {
                if (deletado.id == it.id) {
                    val emailDeletado = it.copy(categoria = categoria)
                    atualizarEmail(context, emailDeletado)
                }
            }
        }
        val listaAtualizadoDoBD = getListaCompletaEmailDb(context)
        onListaCompletaEmailDbChange(listaAtualizadoDoBD)
    }

    fun changeEmailCategoria(
        context: Context,
        email: Email,
        categoria: Categoria
    ) {
        val emailDeletado = email.copy(categoria = categoria)
        atualizarEmail(context, emailDeletado)
        val listaAtualizadoDoBD = getListaCompletaEmailDb(context)
        onListaCompletaEmailDbChange(listaAtualizadoDoBD)
    }

    fun changeAllEmailToOrNotRead(
        context: Context,
        listaDosEmails: List<Email>,
        toOrNotRead: Boolean
    ) {
        for (email in listaDosEmails) {
            _listaCompletaEmailDb.value?.map {
                if (it == email) {
                    val emailAlterado = it.copy(isRead = toOrNotRead)
                    atualizarEmail(context, emailAlterado)
                }
            }
        }
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

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onTextFieldChange("")
        }
    }

    // Historico
    private val _listaHistorico = MutableLiveData<List<HistoricoDeBusca>>()
    val listaHistorico: LiveData<List<HistoricoDeBusca>> = _listaHistorico

    fun onListaHistoricoChange(listaNovaHistorico: List<HistoricoDeBusca>) {
        _listaHistorico.value = listaNovaHistorico
    }

    // todosEmailSelecionados
    private val _todosEmailSelecionados = MutableLiveData<Boolean>()
    val todosEmailSelecionados: LiveData<Boolean> = _todosEmailSelecionados

    fun onTodosEmailSelecionadosChange(novoValor: Boolean) {
        _todosEmailSelecionados.value = novoValor
    }

    // Categoria
    private val _categoria = MutableLiveData<Categoria>()
    val categoria: LiveData<Categoria> = _categoria

    fun onCategoriaChange(categoria: Categoria) {
        _categoria.value = categoria
    }

    // tituloDaCaixaDeEntrada
    private val _tituloDaCaixaDeEntrada = MutableLiveData<String>()
    val tituloDaCaixaDeEntrada: LiveData<String> = _tituloDaCaixaDeEntrada

    fun onTituloDaCaixaDeEntradaChange(novoTitulo: String) {
        _tituloDaCaixaDeEntrada.value = novoTitulo
    }

    // Sidebar
    private val _selectedItemIndex = MutableLiveData<Int>()
    val selectedItemIndex: LiveData<Int> = _selectedItemIndex

    fun onSelectedItemIndex(novoValor: Int) {
        _selectedItemIndex.value = novoValor
    }

    // iconDraftSelected
    private val _iconDraftSelected = MutableLiveData<Boolean>()
    val iconDraftSelected: LiveData<Boolean> = _iconDraftSelected

    fun onIconDraftSelectedChange(novoValor: Boolean) {
        _iconDraftSelected.value = novoValor
    }

    // Email
    private val _email = MutableLiveData<Email>()
    val email: LiveData<Email> = _email

    // marcadorVazio (verifica se o filtro aplicado do marcador está vazio)
    private val _marcadorVazio = MutableLiveData<Boolean>()
    val marcadorVazio: LiveData<Boolean> = _marcadorVazio

    fun onMarcadorVazioChange(newValue: Boolean) {
        _marcadorVazio.value = newValue
    }

    // filtroState
    private val _filtroState = MutableLiveData<Boolean>()
    val filtroState: LiveData<Boolean> = _filtroState

    fun onFiltroStateChange(newValue: Boolean) {
        _filtroState.value = newValue
    }

}