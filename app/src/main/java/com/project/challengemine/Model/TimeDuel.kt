package com.project.challengemine.Model

import com.project.challengemine.Util.Common

class TimeDuel: Duel {
    override fun getDescription(): String {
        return StringBuilder( "Time: ").append( timeDuel.toString() ).toString()
    }

    override fun end() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var timeDuel: Float

    constructor() : super() {
        this.timeDuel = 0F
        this.type = Common.DUEL_TYPE_TIME
    }

    constructor( attacker: User, defender: User, timeDuel: Float ) : super(attacker, defender) {
        this.timeDuel = timeDuel
        this.type = Common.DUEL_TYPE_TIME

    }
}