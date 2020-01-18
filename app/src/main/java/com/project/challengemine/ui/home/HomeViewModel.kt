package com.project.challengemine.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.challengemine.Util.Common

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = StringBuilder( "WPAM\n" +
                "ChallengeMine\n" +
                "Łukasz Wierzbicki 277446")
            .append("\n\n")
            .append( "Logged user: ")
            .append( Common.loggedUser.name ).toString()
    }
    val text: LiveData<String> = _text
}