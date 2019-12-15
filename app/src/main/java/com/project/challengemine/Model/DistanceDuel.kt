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
//        }
//        else {
//            FirebaseDatabase.getInstance()
//                .getReference( Common.USER_INFORMATION )
//                .child( defender!!.uid!! )
//                .child( Common.DUEL_TYPE_DISTANCE )
//                .child( attacker!!.uid!! )
//                .setValue( this )
//        }
    }
    override fun end() {
        ended = true
        if( distanceAttacker > distanceDefender)
            winner = attacker
        else
            winner = defender

//        if( type == Common.DUEL_TYPE_TIME ){
        FirebaseDatabase.getInstance()
            .getReference( Common.USER_INFORMATION )
            .child( defender!!.uid!! )
            .child( Common.DUEL_TYPE_TIME )
            .child( attacker!!.uid!! )
            .setValue( this )
//        }
//        else {
//            FirebaseDatabase.getInstance()
//                .getReference( Common.USER_INFORMATION )
//                .child( defender!!.uid!! )
//                .child( Common.DUEL_TYPE_DISTANCE )
//                .child( attacker!!.uid!! )
//                .setValue( this )
//        }


//        FirebaseDatabase.getInstance()
//            .getReference( Common.USER_INFORMATION )
//            .child( winner!!.uid!! )
//            .child( Common.STATISTICS )
//            .child( "wonDuels" )
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