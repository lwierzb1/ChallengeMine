package com.project.challengemine.Interface

interface IFirebaseLoadDone {
    fun onFirebaseLoadUserDone( lstEmail: List<String> )
    fun onFirebaseLoadUserFailed( message:String )
}