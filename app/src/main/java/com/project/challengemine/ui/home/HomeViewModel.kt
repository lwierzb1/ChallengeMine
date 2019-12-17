package com.project.challengemine.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "WPAM\nChallengeMine\nŁukasz Wierzbicki 277446A"
    }
    val text: LiveData<String> = _text
}