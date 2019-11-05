package com.project.challengemine.Model

import java.util.*

abstract class Duel {
    var attacker: User?
    var defender: User?

    lateinit var type: String

    val winner: User? = null
    val attackerTime: String? = null
    val defenderTime: String? = null

    val requestDate: Date?

    constructor() {
        this.attacker = null
        this.defender = null
        this.requestDate = null
    }
    constructor( attacker: User, defender: User ) {
        this.attacker = attacker
        this.defender = defender
        this.requestDate = Calendar.getInstance().time;
    }

    abstract fun end()
    abstract fun getDescription(): String
    abstract fun getTitle(): String
}