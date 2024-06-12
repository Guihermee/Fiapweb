package br.com.fiap.fiapweb.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EnvioDeEmailViewModel: ViewModel() {

    private val _toFieldValue = MutableLiveData<String>()
    val toFieldValue: LiveData<String> = _toFieldValue

    private val _subjectFieldValue = MutableLiveData<String>()
    val subjectFieldValue: LiveData<String> = _subjectFieldValue

    private val _emailBodyFieldValue = MutableLiveData<String>()
    val emailBodyFieldValue: LiveData<String> = _emailBodyFieldValue

    private val _ccFieldValue = MutableLiveData<String>()
    val ccFieldValue: LiveData<String> = _ccFieldValue

    private val _ccoFieldValue = MutableLiveData<String>()
    val ccoFieldValue: LiveData<String> = _ccoFieldValue

    fun onToFieldValueChanged(novoToFieldValue: String){
        _toFieldValue.value = novoToFieldValue
    }

    fun onSubjectFieldValueChanged(novoSubjectFieldValue: String){
        _subjectFieldValue.value = novoSubjectFieldValue
    }

    fun onEmailBodyFieldValueChanged(novoEmailBodyFieldValue: String){
        _emailBodyFieldValue.value = novoEmailBodyFieldValue
    }

    fun onCcFieldValueChanged(novoCcFieldValue: String) {
        _ccFieldValue.value = novoCcFieldValue
    }

    fun onCcoFieldValueChanged(novoCcoFieldValue: String) {
        _ccoFieldValue.value = novoCcoFieldValue
    }







}