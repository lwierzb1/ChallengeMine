package com.project.challengemine.Util

import com.project.challengemine.Model.User
import com.project.challengemine.Remote.IFCMClient
import com.project.challengemine.Remote.IFCMService

object Common {
    val DUEL_REQUEST: String = "DUEL_REQUEST"
    val DUEL_TYPE: String = "DUEL_TYPE"
    val DUEL_EXTRA_INTENT: String = "DUEL_EXTRA_INTENT"
    val DUEL_TYPE_DISTANCE: String = "DUEL_DISTANCE"
    val DUEL_TYPE_TIME: String = "DUEL_TIME"


    val FROM_USER: String = "FROM_USER"
    val FROM_EMAIL: String = "FROM_EMAIL"
    val FROM_UID: String = "FROM_UID"
    val FROM_STATISTICS: String = "FROM_STATISTICS"

    val TO_USER: String = "TO_USER"
    val TO_EMAIL: String = "TO_EMAIL"
    val TO_UID: String = "TO_UID"
    val TO_STATISTICS: String = "TO_STATISTICS"

    val ACCEPT_LIST: String = "acceptList"
    val STATISTICS: String = "statistics"
    val USER_UID_SAVE_KEY: String? = "SAVE_KEY"
    val TOKENS: String = "TOKENS"
    lateinit var loggedUser: User
    val USER_INFORMATION: String = "UserInformation"


    val ifcmService: IFCMService
        get() = IFCMClient.getClient("https://fcm.googleapis.com")
            .create( IFCMService::class.java )

    val DUEL_TYPES: Array<String> = arrayOf("Distance duel", "Time duel")
}