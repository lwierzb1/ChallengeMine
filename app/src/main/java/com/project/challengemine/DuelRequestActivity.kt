package com.project.challengemine

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mancj.materialsearchbar.MaterialSearchBar
import com.project.challengemine.Interface.IFirebaseLoadDone
import com.project.challengemine.Interface.IRecyclerItemClickListener
import com.project.challengemine.Model.User
import com.project.challengemine.Util.Common
import com.project.challengemine.ViewHolder.DuelRequestViewHolder
import com.project.challengemine.ViewHolder.UserViewHolder
import kotlinx.android.synthetic.main.activity_all_people.*
import kotlinx.android.synthetic.main.activity_all_people.material_search_bar
import kotlinx.android.synthetic.main.activity_duel_request.*

class DuelRequestActivity : AppCompatActivity(), IFirebaseLoadDone {


    var adapter: FirebaseRecyclerAdapter<User, DuelRequestViewHolder>?=null
    var searchAdapter: FirebaseRecyclerAdapter<User, DuelRequestViewHolder>?=null


    lateinit var iFirebaseLoadDone: IFirebaseLoadDone
    var suggestedList:List< String > = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_duel_request)

        material_search_bar.setCardViewElevation( 10 )
        material_search_bar.addTextChangeListener( object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val suggest = ArrayList< String> ()

                for( search in suggestedList )
                    if( search.toLowerCase().contentEquals( material_search_bar.text.toLowerCase() ))
                        suggest.add( search )

                material_search_bar.lastSuggestions = suggest
            }
        })
        material_search_bar.setOnSearchActionListener( object: MaterialSearchBar.OnSearchActionListener{
            override fun onButtonClicked(buttonCode: Int) {

            }

            override fun onSearchStateChanged(enabled: Boolean) {
                if( !enabled ) {
                    //close search -> return default
                    if( adapter != null)
                        recycler_duel_request.adapter = adapter
                }
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                startSearch( text.toString() )
            }
        })

        recycler_duel_request.setHasFixedSize( true )
        val layoutManager = LinearLayoutManager( this )
        recycler_duel_request.layoutManager = layoutManager;
        recycler_duel_request.addItemDecoration( DividerItemDecoration( this, layoutManager.orientation ))

        iFirebaseLoadDone = this

        loadDuelRequestList()
        loadSearchData()
    }

    private fun loadSearchData() {
        //get all user
        val lstUserEmail = ArrayList< String >()
        val userList = FirebaseDatabase.getInstance().getReference( Common.USER_INFORMATION )
            .child( Common.loggedUser.uid!! )
            .child( Common.DUEL_REQUEST )

        userList.addListenerForSingleValueEvent( object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                iFirebaseLoadDone.onFirebaseLoadUserFailed( p0.message )
            }

            override fun onDataChange(p0: DataSnapshot) {
                for( userSnapshot in p0.children ) {
                    val user = userSnapshot.getValue( User::class.java )
                    lstUserEmail.add( user!!.email!! )
                }

                iFirebaseLoadDone.onFirebaseLoadUserDone( lstUserEmail )
            }

        })
    }

    private fun loadDuelRequestList() {
        val query = FirebaseDatabase.getInstance().getReference( Common.USER_INFORMATION )
            .child( Common.loggedUser.uid!! )
            .child( Common.DUEL_REQUEST )

        val options = FirebaseRecyclerOptions.Builder< User >()
            .setQuery( query, User::class.java )
            .build()

        adapter = object:FirebaseRecyclerAdapter< User, DuelRequestViewHolder >( options ) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): DuelRequestViewHolder {
                var itemView = LayoutInflater.from( parent.context )
                    .inflate(R.layout.layout_duel_request, parent, false)

                return DuelRequestViewHolder( itemView )
            }

            override fun onBindViewHolder(holder: DuelRequestViewHolder, position: Int, model: User) {
                holder.txt_user_email.text = model.email

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

    private fun addUserToDuel(model: User) {
        model.statistics!!.duelRequests = model.statistics!!.duelRequests!!.plus(1)

        FirebaseDatabase.getInstance()
            .getReference( Common.USER_INFORMATION )
            .child( model.uid!! ).setValue( model )
    }

    private fun addToAcceptList(model: User) {
        var accpetList = FirebaseDatabase.getInstance().getReference( Common.USER_INFORMATION )
            .child( Common.loggedUser.uid!! )
            .child( Common.ACCEPT_LIST )

        accpetList.child( model.uid!! ).setValue( model )
    }

    private fun deleteDuelRequest(model: User, isShowMessage: Boolean) {
        val duelRequest = FirebaseDatabase.getInstance().getReference( Common.USER_INFORMATION )
            .child( Common.loggedUser.uid!! )
            .child( Common.DUEL_REQUEST )

        duelRequest.child( model.uid!! ).removeValue()
            .addOnSuccessListener {
                run {
                    if (isShowMessage)
                        Toast.makeText(
                            this@DuelRequestActivity,
                            "Remove !",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }

    }

    override fun onStop() {
        if( adapter != null)
            adapter!!.stopListening()
        if( searchAdapter != null)
            searchAdapter!!.stopListening()

        super.onStop()
    }

    override fun onFirebaseLoadUserDone(lstEmail: List<String>) {
        material_search_bar.lastSuggestions = lstEmail
    }

    override fun onFirebaseLoadUserFailed(message: String) {
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show()
    }

    private fun startSearch( searchString: String ) {
        val query = FirebaseDatabase.getInstance().getReference( Common.USER_INFORMATION )
            .child( Common.loggedUser.uid!! )
            .child( Common.DUEL_REQUEST )
            .orderByChild( "email")

        val options = FirebaseRecyclerOptions.Builder< User >()
            .setQuery( query, User::class.java )
            .build()

        searchAdapter = object:FirebaseRecyclerAdapter<User, DuelRequestViewHolder>( options ) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DuelRequestViewHolder {
                var itemView = LayoutInflater.from( parent.context )
                    .inflate(R.layout.layout_duel_request, parent, false)

                return DuelRequestViewHolder( itemView )
            }

            override fun onBindViewHolder(holder: DuelRequestViewHolder, position: Int, model: User) {
                holder.txt_user_email.text = model.email

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
        searchAdapter!!.startListening()

        recycler_duel_request.adapter = searchAdapter;
    }
}
