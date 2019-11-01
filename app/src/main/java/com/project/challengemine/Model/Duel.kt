package com.project.challengemine.Model

abstract class Duel {
    var attacker: User?
    var defender: User?

    lateinit var type: String

    val winner: User? = null
    val attackerTime: String? = null
    val defenderTime: String? = null
    constructor() {
        this.attacker = null
        this.defender = null
    }
    constructor( attacker: User, defender: User) {
        this.attacker = attacker
        this.defender = defender
    }

    abstract fun end()
    abstract fun getDescription(): String
}