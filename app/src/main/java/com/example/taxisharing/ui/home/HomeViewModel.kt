package com.example.taxisharing.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    private val _age = MutableLiveData<Int>().apply {
        value = 90
    }

    val age: LiveData<Int> = _age
    val text: LiveData<String> = _text
}