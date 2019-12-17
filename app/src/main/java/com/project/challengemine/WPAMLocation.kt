package com.project.challengemine

import android.content.Context
import im.delight.android.location.SimpleLocation
import im.delight.android.location.SimpleLocation.Listener

class WPAMLocation {
    private lateinit var location: SimpleLocation

    private var currentLat: Double
    private var lastLat: Double
    private var currentLong: Double
    private var lastLong: Double
    private var distance: Double = 0.0

    constructor( context: Context){
        location = SimpleLocation(context, false, false, 500)
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(context);
        }

        location.beginUpdates()
        currentLat = location.latitude
        lastLat = location.longitude

        currentLong = currentLat
        lastLong = lastLat
    }
    fun process() {
        lastLat = currentLat
        lastLong = currentLong

        currentLat = location.latitude
        currentLong = location.longitude
    }
    fun computeDistance(): Double {
        if( lastLat.compareTo(  currentLat ) != 0 || lastLong.compareTo( currentLong ) != 0)
            distance += SimpleLocation.calculateDistance( lastLat, lastLong, currentLat, currentLong)

        return distance
    }


}