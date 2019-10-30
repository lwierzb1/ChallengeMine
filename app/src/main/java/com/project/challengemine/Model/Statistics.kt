package com.project.challengemine.Model

class Statistics {
    var duelRequests: Int? = null
    var wonDuels: Int? = null
    var points: Int? = null

    constructor() {
        duelRequests = 0
        wonDuels = 0
        points = 0
    }

    constructor( duelRequests: Int, wonDuels: Int, points: Int ){
        this.wonDuels = wonDuels
        this.points = points
        this.duelRequests = duelRequests

    }


}