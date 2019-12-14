package com.project.challengemine.Service

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import com.project.challengemine.Model.User
import java.util.*
import com.google.android.gms.common.api.GoogleApiClient



class GoogleMapsService {

    val timer = Timer()

    private val FusedLoca: GoogleApiClient? = null

    fun startServiceDistance() {
        val task = object: TimerTask() {
            var timesRan = 0
            override fun run() {
                println("timer passed ${++timesRan} time(s)")
            }
        }
        timer.schedule(task, 0, 1000)
    }

    fun startServiceTime() {
        val task = object: TimerTask() {
            var timesRan = 0
            override fun run() {
                println("timer passed ${++timesRan} time(s)")
            }
        }
        timer.schedule(task, 0, 1000)
    }
}