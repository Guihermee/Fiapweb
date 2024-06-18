package br.com.fiap.fiapweb.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.fiap.fiapweb.model.Email
import br.com.fiap.fiapweb.model.Marcadores

class TelaLerEmailViewModel : ViewModel() {


    // bookMarkState (icone do header ler email)
    private val _modalCriarState = MutableLiveData<Boolean>()
    val modalCriarState: LiveData<Boolean> = _modalCriarState

    fun onModalCriarStateChange(newValue: Boolean) {
        _modalCriarState.value = newValue
    }

    // modalAdicionarState
    private val _modalAdicionarState = MutableLiveData<Boolean>()
    val modalAdicionarState: LiveData<Boolean> = _modalAdicionarState

    fun onModalAdicionarStateChange(newValue: Boolean) {
        _modalAdicionarState.value = newValue
    }

    // modal modalAIGenerateResumeState
    private val _modalAIGenerateResumeState = MutableLiveData<Boolean>()
    val modalAIGenerateResumeState: LiveData<Boolean> = _modalAIGenerateResumeState

    fun onModalAIGenerateResumeStateChange(newValue: Boolean) {
        _modalAIGenerateResumeState.value = newValue
    }

    // responseResume (resposta do Resumo do Email da Inteligencia artificial)
    private val _responseResume = MutableLiveData<String>()
    val responseResume: LiveData<String> = _responseResume

    fun onResponseResumeChange(newValue: String) {
        _responseResume.value = newValue
    }

    // bookmarkNomeField
    private val _bookmarkNomeField = MutableLiveData<String>()
    val bookmarkNomeField: LiveData<String> = _bookmarkNomeField

    fun onBookmarkNomeFieldChange(newValue: String) {
        _bookmarkNomeField.value = newValue
    }

    // verificaListaMarcadores
    private val _verificaListaMarcadores = MutableLiveData<Boolean>()
    val verificaListaMarcadores: LiveData<Boolean> = _verificaListaMarcadores

    fun onVerificaListaMarcadoresChange(newValue: Boolean) {
        _verificaListaMarcadores.value = newValue
    }

    // dropdownState
    private val _dropdownState = MutableLiveData<Boolean>()
    val dropdownState: LiveData<Boolean> = _dropdownState

    fun onDropdownStateChange(newValue: Boolean) {
        _dropdownState.value = newValue
    }

    // selectedMarcador
    private val _selectedMarcador = MutableLiveData<Marcadores>()
    val selectedMarcador: LiveData<Marcadores> = _selectedMarcador

    fun onSelectedMarcadorChange(newValue: Marcadores) {
        _selectedMarcador.value = newValue
    }

    // lista do dropdown
    private val _listaDropdown = MutableLiveData<List<Marcadores>>()
    val listaDropdown: LiveData<List<Marcadores>> = _listaDropdown

    fun onListaDropdownChange(newValue: List<Marcadores>) {
        _listaDropdown.value = newValue
    }

    // Email
    private val _email = MutableLiveData<Email>()
    val email: LiveData<Email> = _email

}