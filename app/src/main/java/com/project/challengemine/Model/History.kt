package com.project.challengemine.Model

class History {
    lateinit var winnerName: String
    lateinit var opponentName: String

    constructor( winnerName: String, opponentName : String) {
        this.winnerName = winnerName
        this.opponentName = opponentName
    }
}