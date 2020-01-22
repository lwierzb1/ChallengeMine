package com.project.challengemine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.challengemine.Model.TimeDuel
import com.project.challengemine.Model.User
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
        var user : User
        var userDB = FirebaseDatabase.getInstance()
            .getReference( Common.USER_INFORMATION )
            .child( Common.loggedUser.uid!! )



        userDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if( p0.getValue(User::class.java) != null) {
                    user = p0.getValue(User::class.java)!!


                    history_txt_all_requests.text = user.statistics!!.duelRequests.toString()
                    history_txt_won_duels.text = user.statistics!!.wonDuels.toString()
                    history_txt_points.text = user.statistics!!.points.toString()
                }
            }

        })

        history_txt_user_name = findViewById( R.id.history_txt_user_name ) as TextView
        history_txt_user_email = findViewById( R.id.history_txt_user_email ) as TextView

        history_txt_all_requests = findViewById( R.id.history_txt_all_requests ) as TextView
        history_txt_won_duels = findViewById( R.id.history_txt_won_duels ) as TextView
        history_txt_points = findViewById( R.id.history_txt_points ) as TextView

        history_txt_user_name.text = Common.loggedUser.name
        history_txt_user_email.text = Common.loggedUser.email

    }
}
