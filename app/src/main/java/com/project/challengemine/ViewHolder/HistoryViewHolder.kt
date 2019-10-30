package com.project.challengemine.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.challengemine.R

class HistoryViewHolder( itemView: View): RecyclerView.ViewHolder( itemView ) {
    var history_txt_user_name: TextView
    var history_txt_user_email: ImageView

    var history_txt_all_requests: ImageView
    var history_txt_won_duels: ImageView
    var history_txt_points: ImageView


    init {
        history_txt_user_name = itemView.findViewById( R.id.history_txt_user_name )
        history_txt_user_email = itemView.findViewById( R.id.history_txt_user_email )
        history_txt_all_requests = itemView.findViewById( R.id.history_txt_all_requests )
        history_txt_won_duels = itemView.findViewById( R.id.history_txt_won_duels )
        history_txt_points = itemView.findViewById( R.id.history_txt_points )
    }
}