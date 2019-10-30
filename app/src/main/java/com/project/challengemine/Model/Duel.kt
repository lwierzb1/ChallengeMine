package com.project.challengemine.Model

class Duel {
    var attacker: User
    var defender: User
    var type: String
    lateinit var winner: User
    lateinit var attackerTime: String
    lateinit var defenderTime: String

    constructor( attacker: User, defender: User, type: String) {
        this.attacker = attacker
        this.defender = defender
        this.type = type
    }
}