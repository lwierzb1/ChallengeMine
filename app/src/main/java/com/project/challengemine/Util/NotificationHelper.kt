package com.project.challengemine.Util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.media.AudioAttributes
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.project.challengemine.R

class NotificationHelper(base: Context): ContextWrapper( base ) {

    private val manager:NotificationManager?=null

    companion object {
        private val MINE_CHANNEL_ID = "com.project.challengemine"
        private val MINE_CHANNEL_NAME = "challengemine"
    }

    init {
        if( Build.VERSION.SDK_INT > Build.VERSION_CODES.O ){
             createChannel( RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION ))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(defaultUri: Uri?) {
        val mineChannel = NotificationChannel( MINE_CHANNEL_ID, MINE_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT )

        mineChannel.enableLights( true )
        mineChannel.enableVibration( true )

        mineChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        val audioAttributes = AudioAttributes.Builder()
            .setContentType( AudioAttributes.CONTENT_TYPE_SONIFICATION )
            .setUsage( AudioAttributes.USAGE_NOTIFICATION_RINGTONE )
            .build()

        mineChannel.setSound( defaultUri!!, audioAttributes)

        getManager().createNotificationChannel( mineChannel )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getChallengeMineNotification(title: String, content: String) : Notification.Builder {

        return Notification.Builder( applicationContext, MINE_CHANNEL_ID )
            .setSmallIcon( R.mipmap.ic_launcher )
            .setContentTitle( title )
            .setContentText( content )
            .setAutoCancel( false )

    }
    fun getManager(): NotificationManager {
        if( manager == null )
            return  getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager
        return  manager;
    }
}