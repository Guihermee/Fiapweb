package br.com.fiap.fiapweb.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModalOpenAIViewModel: ViewModel() {

    // Prompt
    private val _promptValue = MutableLiveData<String>()
    val promptValue: LiveData<String> = _promptValue

    fun onPromptValueChange(promptNovoValor: String) {
        _promptValue.value = promptNovoValor
    }

}