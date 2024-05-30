package br.com.fiap.fiapweb.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TelaInicialViewModel : ViewModel() {

    private val _textField = MutableLiveData<String>()
    val textField: LiveData<String> = _textField

    fun onTextFieldChange(textFieldCampo: String) {
        _textField.value = textFieldCampo
    }

}