package com.project.challengemine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.project.challengemine.Model.DistanceDuel
import com.project.challengemine.Model.TimeDuel
import com.project.challengemine.Model.User
import com.project.challengemine.Util.Common

class DistanceDuelActivity : AppCompatActivity() {

    lateinit var distance_duel_title: TextView

    lateinit var attakcer_txt: TextView
    lateinit var defender_txt: TextView
    lateinit var timer_elapsed: TextView

    lateinit var attacker_distance: ProgressBar
    lateinit var defender_distance: ProgressBar
    lateinit var button_start: Button

    lateinit var duel: DistanceDuel
    lateinit var duelDB: DistanceDuel

    val oneSecondHandler = Handler()
    lateinit var opponent: User

    var duelStarted: Boolean = false
    var wpamTimer = WPAMTimer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distance_duel)

        distance_duel_title = findViewById( R.id.distance_duel_title ) as TextView

        attakcer_txt = findViewById( R.id.distance_text_attacker ) as TextView
        defender_txt = findViewById( R.id.distance_text_defender ) as TextView
        timer_elapsed = findViewById( R.id.time_elapsed ) as TextView
        timer_elapsed.setVisibility( View.INVISIBLE )

        button_start = findViewById( R.id.button_start ) as Button
        button_start.setEnabled( false )

        duel = Gson().fromJson( intent.getStringExtra( Common.DUEL_EXTRA_INTENT ), DistanceDuel::class.java)

        attakcer_txt.text = duel.attacker!!.name
        defender_txt.text = duel.defender!!.name

        distance_duel_title.text = duel.getTitle()

        attacker_distance = findViewById( R.id.distance_progress_atacker ) as ProgressBar
        defender_distance = findViewById( R.id.distance_progress_defender ) as ProgressBar

        val duelUser= FirebaseDatabase.getInstance().getReference(Common.USER_INFORMATION)
            .child( duel.defender!!.uid!! )
            .child( Common.DUEL_TYPE_DISTANCE )
            .child( duel.attacker!!.uid!! )

        if( Common.loggedUser.uid.equals( duel.defender!!.uid )) {
            duelUser.child("opponentOnline").setValue(true)
            button_start.setVisibility( View.INVISIBLE )
        }

        button_start.setOnClickListener {
            duelUser.child("started").setValue( true )
            button_start.setVisibility( View.INVISIBLE )
        }

        oneSecondHandler.postDelayed(object : Runnable {
            override fun run() {
                if (::duelDB.isInitialized) {

                    if (duelDB.started) {
                        wpamTimer.incSeconds()
                        timer_elapsed.setText(wpamTimer.timeToString())
                        timer_elapsed.setVisibility(View.VISIBLE)
                    }

                    if (duelDB.ended)
                        onDuelStop()
                }

                duelUser.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}
                    override fun onDataChange(p0: DataSnapshot) {
                        if( p0.getValue(DistanceDuel::class.java) != null ) {
                            duelDB = p0.getValue(DistanceDuel::class.java)!!
                            button_start.setEnabled(duelDB.opponentOnline)
                        }
                    }

                })
                oneSecondHandler.postDelayed(this, 1000)
            }
        }, 100)
    }

    private fun onDuelStop() {
        oneSecondHandler.removeCallbacksAndMessages(null);
        //timeIsUpHandler.removeCallbacksAndMessages( null )

        Toast.makeText( this, "Distance achieved!!!",
            Toast.LENGTH_LONG).show();
    }
    override fun onStop() {
        super.onStop()
        oneSecondHandler.removeCallbacksAndMessages(null);
    }
}
