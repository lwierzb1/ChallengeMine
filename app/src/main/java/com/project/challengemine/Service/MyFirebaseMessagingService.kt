package com.project.challengemine.Service

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.project.challengemine.Model.User
import com.project.challengemine.R
import com.project.challengemine.Util.Common
import com.project.challengemine.Util.NotificationHelper
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0);
        val user = FirebaseAuth.getInstance().currentUser
        if ( user != null ) {
            val tokens = FirebaseDatabase.getInstance().getReference(Common.TOKENS)
            tokens.child(user.uid).setValue(p0)
        }
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
            sendNotificationWithChannel(p0)
        }
        else
            sendNotification( p0 )
        addRequestToUserInformation(p0.data)
    }

    private fun sendNotificationWithChannel(p0: RemoteMessage) {
        val data = p0.data
        val title = "Duel request"
        val content = StringBuilder("New running duel request ")
            .append( data[ Common.FROM_USER ] )
            .append( " ( ")
            .append( data[ Common.FROM_EMAIL ] )
            .append( " )").toString()
        val helper: NotificationHelper = NotificationHelper( this )
        val builder: Notification.Builder = helper.getChallengeMineNotification( title, content )

        helper.getManager().notify( Random.nextInt(), builder.build() )

    }

    private fun sendNotification(p0: RemoteMessage) {
        val data = p0.data
        val title = "Duel Request"
        val content = StringBuilder("New running duel request ")
            .append( data[ Common.FROM_USER ] )
            .append( " ( ")
            .append( data[ Common.FROM_EMAIL ] )
            .append( " )").toString()

        val builder = NotificationCompat.Builder( this, "" )
            .setSmallIcon( R.mipmap.ic_launcher_round )
            .setContentText( content )
            .setContentTitle( title )
            .setAutoCancel( false )

        val manager = getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager
        manager.notify( Random.nextInt(), builder.build() )
    }

    private fun addRequestToUserInformation(data: Map<String, String>) {
        val duelRequest = FirebaseDatabase.getInstance()
            .getReference( Common.USER_INFORMATION )
            .child( data[ Common.TO_UID ]!! )
            .child( Common.DUEL_REQUEST )

        val user = User( data[ Common.FROM_UID]!!, data[ Common.FROM_EMAIL]!!, data[ Common.FROM_USER]!! )
        duelRequest.child( user.uid!! ).setValue( user )
    }
}