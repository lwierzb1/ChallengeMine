package com.project.challengemine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import com.google.gson.Gson
import com.project.challengemine.Model.DistanceDuel
import com.project.challengemine.Util.Common

class DistanceDuelActivity : AppCompatActivity() {

    lateinit var distance_duel_title: TextView

    lateinit var attakcer_txt: TextView
    lateinit var defender_txt: TextView

    lateinit var attacker_distance: ProgressBar
    lateinit var defender_distance: ProgressBar

    lateinit var duel: DistanceDuel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distance_duel)

        distance_duel_title = findViewById( R.id.distance_duel_title ) as TextView

        attakcer_txt = findViewById( R.id.distance_text_attacker ) as TextView
        defender_txt = findViewById( R.id.distance_text_defender ) as TextView

        duel = Gson().fromJson( intent.getStringExtra( Common.DUEL_EXTRA_INTENT ), DistanceDuel::class.java)

        attakcer_txt.text = duel.attacker!!.name
        defender_txt.text = duel.defender!!.name

        distance_duel_title.text = duel.getTitle()

        attacker_distance = findViewById( R.id.distance_progress_atacker ) as ProgressBar
        defender_distance = findViewById( R.id.distance_progress_defender ) as ProgressBar
    }
}
