package br.com.fiap.fiapweb.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TelaInicialViewModel : ViewModel() {

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

}