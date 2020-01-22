package com.project.challengemine.Model

import com.google.firebase.database.FirebaseDatabase
import com.project.challengemine.Util.Common

class DistanceDuel: Duel {
    override fun getTitle(): String {
        return StringBuilder( distanceDuel.toString())
            .append( " km run")
            .toString()
    }

    override fun getDescription(): String {
        return StringBuilder( "Distance: ")
            .append( distanceDuel.toString() )
            .append( " km")
            .toString()
    }

    override fun start() {
        started = true
//        if( type == Common.DUEL_TYPE_TIME ){
        FirebaseDatabase.getInstance()
            .getReference( Common.USER_INFORMATION )
            .child( defender!!.uid!! )
            .child( Common.DUEL_TYPE_TIME )
            .child( attacker!!.uid!! )
            .setValue( this )
    }
    override fun end() {
        ended = true
        if( distanceAttacker > distanceDefender)
            winner = attacker
        else
            winner = defender

        if( Common.loggedUser.uid.equals( winner!!.uid) ){
            Common.loggedUser.statistics!!.points = Common.loggedUser.statistics!!.points!! + 100
            Common.loggedUser.statistics!!.wonDuels = Common.loggedUser.statistics!!.wonDuels!! + 1
            winner!!.statistics = Common.loggedUser.statistics

            val duelUser= FirebaseDatabase.getInstance().getReference(Common.USER_INFORMATION)
                .child( winner!!.uid!! )
                .child( "statistics" )

            duelUser.child("points" ).setValue( winner!!.statistics!!.points!! )
            duelUser.child( "wonDuels" ).setValue( winner!!.statistics!!.wonDuels!! );
        }

        val defenderUser= FirebaseDatabase.getInstance().getReference(Common.USER_INFORMATION)
            .child( defender!!.uid!! )
            .child( "DUEL_DISTANCE" )
            .removeValue()


    }

    var distanceDuel: Float

    constructor() : super() {
        this.distanceDuel = 0F
        this.type = Common.DUEL_TYPE_DISTANCE
        this.started = false
    }

    constructor( attacker: User, defender: User, distanceDuel: Float ) : super(attacker, defender) {
        this.distanceDuel = distanceDuel
        this.type = Common.DUEL_TYPE_DISTANCE
        this.started = false
    }
}