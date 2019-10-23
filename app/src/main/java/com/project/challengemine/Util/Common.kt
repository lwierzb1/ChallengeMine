package com.project.challengemine.Util

import com.project.challengemine.Model.User
import com.project.challengemine.Remote.IFCMClient
import com.project.challengemine.Remote.IFCMService

object Common {
    val FROM_USER: String = "FROM_USER"
    val FROM_EMAIL: String = "FROM_EMAIL"
    val FROM_UID: String = "FROM_UID"

    val TO_USER: String = "TO_USER"
    val TO_EMAIL: String = "TO_EMAIL"
    val TO_UID: String = "TO_UID"

    val ACCEPT_LIST: String = "acceptList"
    val USER_UID_SAVE_KEY: String? = "SAVE_KEY"
    val TOKENS: String = "TOKENS"
    lateinit var loggedUser: User
    val USER_INFORMATION: String = "UserInformation"


    val httpService: IFCMService
        get() = IFCMClient.getClient("https://fcm.googleapis.com")
            .create( IFCMService::class.java )
}