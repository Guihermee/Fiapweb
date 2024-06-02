package br.com.fiap.fiapweb.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EnvioDeEmailViewModel: ViewModel() {

    private val _toFieldValue = MutableLiveData<String>()
    val toFieldValue: LiveData<String> = _toFieldValue

    private val _subjectFieldValue = MutableLiveData<String>()
    val subjectFieldValue: LiveData<String> = _subjectFieldValue

    private val _fromFieldValue = MutableLiveData<String>()
    val fromFieldValue: LiveData<String> = _fromFieldValue

    private val _emailBodyFieldValue = MutableLiveData<String>()
    val emailBodyFieldValue: LiveData<String> = _emailBodyFieldValue

    fun onToFieldValueChanged(novoToFieldValue: String){
        _toFieldValue.value = novoToFieldValue
    }

    fun onFromFieldValueChanged(novoFromFieldValue: String){
        _fromFieldValue.value = novoFromFieldValue
    }

    fun onSubjectFieldValueChanged(novoSubjectFieldValue: String){
        _subjectFieldValue.value = novoSubjectFieldValue
    }

    fun onEmailBodyFieldValueChanged(novoEmailBodyFieldValue: String){
        _emailBodyFieldValue.value = novoEmailBodyFieldValue
    }







}