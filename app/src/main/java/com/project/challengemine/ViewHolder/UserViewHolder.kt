package com.project.challengemine.ViewHolder

import android.view.View
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.project.challengemine.Interface.IRecyclerItemClickListener
import com.project.challengemine.R
import kotlinx.android.synthetic.main.layout_user.view.*

class UserViewHolder( itemView: View): RecyclerView.ViewHolder( itemView ), View.OnClickListener {

    var txt_user_email: TextView;
    lateinit var iRecyclerItemClickListener:IRecyclerItemClickListener

    fun setClick( iRecyclerItemClickListener: IRecyclerItemClickListener ) {
        this.iRecyclerItemClickListener = iRecyclerItemClickListener
    }

    init {
        txt_user_email = itemView.findViewById( R.id.txt_user_email ) as TextView
        itemView.setOnClickListener( this )
    }

    override fun onClick(p0: View?) {
        iRecyclerItemClickListener.onItemClickListener( p0!!, adapterPosition )
    }
}