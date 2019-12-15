package com.project.challengemine.Model

import com.google.firebase.database.FirebaseDatabase
import com.project.challengemine.Util.Common

class TimeDuel: Duel {
    override fun getDescription(): String {
        return StringBuilder( "Time: ")
            .append( timeDuel.toString() )
            .append( " min")
            .toString()
    }

    override fun getTitle(): String {
        return StringBuilder( timeDuel.toString())
            .append( " min run")
            .toString()
    }
    override fun start(){
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

    var timeDuel: Float

    constructor() : super() {
        this.timeDuel = 0F
        this.type = Common.DUEL_TYPE_TIME
        this.started = false
    }

    constructor( attacker: User, defender: User, timeDuel: Float ) : super(attacker, defender) {
        this.timeDuel = timeDuel
        this.type = Common.DUEL_TYPE_TIME
        this.started = false

    }
}