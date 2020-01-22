package com.project.challengemine.Remote

import com.google.firebase.messaging.RemoteMessage
import com.project.challengemine.Model.Duel
import com.project.challengemine.Model.MyResponse
import com.project.challengemine.Model.Request
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IFCMService {
    @Headers( "Content-Type:application/json",
        "Authorization:key=AAAAsaPffLc:APA91bFnwHd511Yia9jFGTqB0p4Z1WEZYP5FojFKhYwlg-WkN2h1TDD2QVwoc9Udthu2uqgsgBCcwFf_65icyvuIAB0ThsoqRGjRLTOwDXdXo5wVeoitH6ED6EZ6rfxQFLbbQKIO6cbx")
    @POST("/fcm/send")
    fun sendDuelRequestToUser( @Body body: Request): Observable<MyResponse>

}