package com.project.challengemine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.challengemine.Interface.IFirebaseLoadDuelDone
import com.project.challengemine.Model.DistanceDuel
import com.project.challengemine.Model.Duel
import com.project.challengemine.Util.Common
import com.project.challengemine.ViewHolder.DuelRequestViewHolder

class DistanceFragment() : Fragment(), IFirebaseLoadDuelDone {
    var adapter: FirebaseRecyclerAdapter<DistanceDuel, DuelRequestViewHolder>? = null


    lateinit var iFirebaseLoadDuelDone: IFirebaseLoadDuelDone
    lateinit var recycler_duel_request: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.distance_fragment, container, false)
        recycler_duel_request = view.findViewById(R.id.recycler_duel_request) as RecyclerView
        recycler_duel_request.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        recycler_duel_request.layoutManager = layoutManager;
        recycler_duel_request.addItemDecoration(
            DividerItemDecoration(
                activity,
                layoutManager.orientation
            )
        )

        iFirebaseLoadDuelDone = this

        loadDuelRequestList()
        loadSearchData()

        return view
    }

    private fun loadSearchData() {
        //get all user
        val lstDuel = ArrayList<DistanceDuel>()
        val userList = FirebaseDatabase.getInstance().getReference(Common.USER_INFORMATION)
            .child(Common.loggedUser.uid!!)
            .child(Common.DUEL_TYPE_DISTANCE)

        userList.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                iFirebaseLoadDuelDone.onFirebaseLoadDuelFailed(p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (userSnapshot in p0.children) {
                    val duel = userSnapshot.getValue(DistanceDuel::class.java)
                    lstDuel.add(duel!!)
                }

                iFirebaseLoadDuelDone.onFirebaseLoadDuelDone(lstDuel)
            }

        })
    }

    private fun loadDuelRequestList() {
        val query = FirebaseDatabase.getInstance().getReference(Common.USER_INFORMATION)
            .child(Common.loggedUser.uid!!)
            .child(Common.DUEL_TYPE_DISTANCE)

        val options = FirebaseRecyclerOptions.Builder<DistanceDuel>()
            .setQuery(query, DistanceDuel::class.java)
            .build()

        adapter = object : FirebaseRecyclerAdapter<DistanceDuel, DuelRequestViewHolder>(options) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): DuelRequestViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_duel_request, parent, false)

                return DuelRequestViewHolder(itemView)
            }

            override fun onBindViewHolder(
                holder: DuelRequestViewHolder,
                position: Int,
                model: DistanceDuel
            ) {
                holder.txt_user_email.text = model.attacker!!.email
                holder.txt_descrpition.text = model.getDescription()

                holder.btn_decline.setOnClickListener {
                    deleteDuelRequest(model, true)

                }
                holder.btn_accept.setOnClickListener {
                    deleteDuelRequest(model, false)
                    addToAcceptList(model)
                    addUserToDuel(model)

                }
            }

        }

        adapter!!.startListening()
        recycler_duel_request.adapter = adapter;
    }

    private fun addUserToDuel(model: DistanceDuel) {
        model.attacker!!.incrementDuelRequestsAndSaveToDB()
        model.defender!!.incrementDuelRequestsAndSaveToDB()

    }

    private fun addToAcceptList(model: DistanceDuel) {
        FirebaseDatabase.getInstance().getReference(Common.USER_INFORMATION)
            .child(Common.loggedUser.uid!!)
            .child(Common.ACCEPT_LIST)
            .child(model.attacker!!.uid!!)
            .setValue(model)
    }

    private fun deleteDuelRequest(model: DistanceDuel, isShowMessage: Boolean) {
        val duelRequest = FirebaseDatabase.getInstance().getReference(Common.USER_INFORMATION)
            .child(Common.loggedUser.uid!!)
            .child(Common.DUEL_TYPE_DISTANCE)

        duelRequest.child(model.attacker!!.uid!!).removeValue()
            .addOnSuccessListener {
                run {
                    if (isShowMessage)
                        Toast.makeText(
                            getActivity(),
                            "Remove !",
                            Toast.LENGTH_SHORT
                        ).show()

                }
            }

    }

    override fun onStop() {
        if (adapter != null)
            adapter!!.stopListening()

        super.onStop()
    }

    override fun onFirebaseLoadDuelDone(lstDuel: List<Duel>) {
//        material_search_bar.lastSuggestions = lstEmail
    }

    override fun onFirebaseLoadDuelFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}

