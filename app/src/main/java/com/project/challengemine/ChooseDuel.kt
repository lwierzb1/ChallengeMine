package com.project.challengemine

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.RadioButton
import com.project.challengemine.Util.Common

class ChooseDuel : AppCompatActivity() {

    lateinit var radioDistance: RadioButton
    lateinit var radioTime: RadioButton

    lateinit var layoutDistance: LinearLayout
    lateinit var layoutTime: LinearLayout

    lateinit var pickerDistance: NumberPicker
    lateinit var pickerTime: NumberPicker

    lateinit var buttonOk: Button
    lateinit var buttonCancel: Button

    lateinit var duelType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_duel)

        radioDistance = findViewById( R.id.choose_distance_duel ) as RadioButton
        radioTime = findViewById( R.id.choose_time_duel ) as RadioButton

        pickerDistance = findViewById( R.id.choose_km_duel ) as NumberPicker
        pickerTime = findViewById( R.id.choose_m_duel ) as NumberPicker

        layoutDistance = findViewById( R.id.layout_choose_km_duel ) as LinearLayout
        layoutTime = findViewById( R.id.layout_choose_m_duel ) as LinearLayout

        buttonOk = findViewById( R.id.choose_ok ) as Button
        buttonCancel = findViewById( R.id.choose_cancel ) as Button

        layoutDistance.visibility = View.GONE
        layoutTime.visibility = View.GONE

        pickerDistance.minValue = 1
        pickerDistance.maxValue = 50

        pickerTime.minValue = 2
        pickerTime.maxValue = 10

        radioDistance.setOnClickListener {
            layoutTime.visibility = View.GONE
            layoutDistance.visibility  = View.VISIBLE
            duelType = Common.DUEL_TYPE_DISTANCE

            buttonOk.isEnabled = true
        }

        radioTime.setOnClickListener {
            layoutDistance.visibility = View.GONE
            layoutTime.visibility = View.VISIBLE
            duelType = Common.DUEL_TYPE_TIME
            buttonOk.isEnabled = true
        }

        buttonOk.isEnabled = false

        buttonOk.setOnClickListener {
            val data = Intent()
            if( duelType == Common.DUEL_TYPE_DISTANCE )
                data.putExtra("distance", pickerDistance.value.toString() )
            else
                data.putExtra( "time", pickerTime.value.toString() )

            data.putExtra("duelType",  duelType )
            data.putExtra("model", intent.getStringExtra("model") )
            setResult(Activity.RESULT_OK, data)
            finish()
        }

        buttonCancel.setOnClickListener{
            finish()
        }
    }
}
