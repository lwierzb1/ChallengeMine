package com.project.challengemine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import com.google.gson.Gson
import com.project.challengemine.Model.DistanceDuel
import com.project.challengemine.Model.TimeDuel
import com.project.challengemine.Util.Common
import java.util.*

class TimeDuelActivity : AppCompatActivity() {

    lateinit var time_duel_title: TextView

    lateinit var attakcer_txt: TextView
    lateinit var defender_txt: TextView

    lateinit var attacker_time: ProgressBar
    lateinit var defender_time: ProgressBar

    lateinit var duel: TimeDuel
    val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_duel)

        time_duel_title = findViewById( R.id.time_duel_title ) as TextView

        attakcer_txt = findViewById( R.id.time_text_attacker ) as TextView
        defender_txt = findViewById( R.id.time_text_defender ) as TextView

        duel = Gson().fromJson( intent.getStringExtra( Common.DUEL_EXTRA_INTENT ), TimeDuel::class.java)

        attakcer_txt.text = duel.attacker!!.name
        defender_txt.text = duel.defender!!.name

        time_duel_title.text = duel.getTitle()

        attacker_time = findViewById( R.id.time_progress_atacker ) as ProgressBar
        defender_time = findViewById( R.id.time_progress_defender ) as ProgressBar

        val task = object: TimerTask() {
            var timesRan = 0
            override fun run() {
                println("timer passed ${++timesRan} time(s)")
            }
        }
        timer.schedule(task, 0, 1000)
    }
}
