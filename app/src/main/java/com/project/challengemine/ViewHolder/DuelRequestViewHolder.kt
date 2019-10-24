package com.project.challengemine.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.challengemine.R

class DuelRequestViewHolder( itemView: View): RecyclerView.ViewHolder( itemView ) {
    var txt_user_email: TextView
    var btn_accept: ImageView
    var btn_decline: ImageView

    init {
        txt_user_email = itemView.findViewById( R.id.txt_user_email )
        btn_accept = itemView.findViewById( R.id.btn_accept )
        btn_decline = itemView.findViewById( R.id.btn_decline )
    }
}