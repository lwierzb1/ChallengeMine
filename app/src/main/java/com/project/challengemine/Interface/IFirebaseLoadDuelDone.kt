package com.project.challengemine.Interface

import com.project.challengemine.Model.Duel

interface IFirebaseLoadDuelDone {
    fun onFirebaseLoadDuelDone( lstDuel: List<Duel> )
    fun onFirebaseLoadDuelFailed( message:String )
}