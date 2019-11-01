package com.project.challengemine.Model

import com.project.challengemine.Util.Common

class DistanceDuel: Duel {
    override fun getDescription(): String {
        return StringBuilder( "Distance: ")
            .append( distanceDuel.toString() )
            .append( " km")
            .toString()
    }

    override fun end() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var distanceDuel: Float

    constructor() : super() {
        this.distanceDuel = 0F
        this.type = Common.DUEL_TYPE_DISTANCE
    }

    constructor( attacker: User, defender: User, distanceDuel: Float ) : super(attacker, defender) {
        this.distanceDuel = distanceDuel
        this.type = Common.DUEL_TYPE_DISTANCE
    }
}