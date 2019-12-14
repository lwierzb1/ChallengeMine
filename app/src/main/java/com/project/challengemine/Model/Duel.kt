package com.project.challengemine.Model

import java.util.*

abstract class Duel {
    var attacker: User?
    var defender: User?

    var opponentOnline: Boolean = false
    var ended: Boolean = false
    var started: Boolean = false

    lateinit var type: String

    var winner: User? = null
    val attackerTime: String? = null
    val defenderTime: String? = null

    var distanceAttacker: Double = 0.0
    var distanceDefender: Double = 0.0

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
    abstract fun start()
    abstract fun getDescription(): String
    abstract fun getTitle(): String
}