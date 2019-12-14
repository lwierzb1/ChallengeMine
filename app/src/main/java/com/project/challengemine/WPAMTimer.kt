package com.project.challengemine

class WPAMTimer {
    var seconds: Int = 0

    fun incSeconds(){
        seconds += 1
    }

    fun timeToString(): String {
        val min = seconds / 60
        val sec = seconds % 60
        val sb = StringBuilder()

        return sb.append( min ).append(":").append( sec ).toString()
    }
}