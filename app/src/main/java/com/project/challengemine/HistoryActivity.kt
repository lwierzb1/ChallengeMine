package com.project.challengemine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import com.project.challengemine.Util.Common

class HistoryActivity : AppCompatActivity() {

    lateinit var history_txt_user_name: TextView
    lateinit var history_txt_user_email: TextView
    lateinit var history_txt_all_requests: TextView
    lateinit var history_txt_won_duels: TextView
    lateinit var history_txt_points: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        history_txt_user_name = findViewById( R.id.history_txt_user_name ) as TextView
        history_txt_user_email = findViewById( R.id.history_txt_user_email ) as TextView

        history_txt_all_requests = findViewById( R.id.history_txt_all_requests ) as TextView
        history_txt_won_duels = findViewById( R.id.history_txt_won_duels ) as TextView
        history_txt_points = findViewById( R.id.history_txt_points ) as TextView

        history_txt_user_name.text = Common.loggedUser.name
        history_txt_user_email.text = Common.loggedUser.email

        history_txt_all_requests.text = Common.loggedUser.statistics!!.duelRequests.toString()
        history_txt_won_duels.text = Common.loggedUser.statistics!!.wonDuels.toString()
        history_txt_points.text = Common.loggedUser.statistics!!.points.toString()

    }
}
