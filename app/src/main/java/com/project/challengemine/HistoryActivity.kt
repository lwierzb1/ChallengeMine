package com.project.challengemine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import com.project.challengemine.Util.Common

class HistoryActivity : AppCompatActivity() {

    lateinit var history_txt_user_name: TextView
    lateinit var history_txt_user_email: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        history_txt_user_name = findViewById( R.id.history_txt_user_name ) as TextView
        history_txt_user_email = findViewById( R.id.history_txt_user_email ) as TextView

        history_txt_user_name.text = Common.loggedUser.name
        history_txt_user_email.text = Common.loggedUser.email

    }
}
