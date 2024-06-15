package br.com.fiap.fiapweb.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TelaLerEmailViewModel : ViewModel(){


    // bookMarkState (icone do header ler email
    private val _bookMarkState = MutableLiveData<Boolean>()
    val bookMarkState: LiveData<Boolean> = _bookMarkState

    fun onBookMarkStateChange(newValue: Boolean) {
        _bookMarkState.value = newValue
    }

}