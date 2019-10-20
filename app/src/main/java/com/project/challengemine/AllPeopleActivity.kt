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
import com.project.challengemine.ViewHolder.UserViewHolder
import kotlinx.android.synthetic.main.activity_all_people.*

class AllPeopleActivity : AppCompatActivity(), IFirebaseLoadDone {

    var adapter:FirebaseRecyclerAdapter<User, UserViewHolder>?=null
    var searchAdapter:FirebaseRecyclerAdapter<User, UserViewHolder>?=null


    lateinit var iFirebaseLoadDone: IFirebaseLoadDone
    var suggestedList:List< String > = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_people)

        material_search_bar.setCardViewElevation( 10 )
        material_search_bar.addTextChangeListener( object:TextWatcher{
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
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSearchStateChanged(enabled: Boolean) {
                if( !enabled ) {
                    //close search -> return default
                    if( adapter != null)
                        recycler_all_people.adapter = adapter
                }
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                startSearch( text.toString() )
            }
        })

        recycler_all_people.setHasFixedSize( true )
        val layoutManager = LinearLayoutManager( this )
        recycler_all_people.layoutManager = layoutManager;
        recycler_all_people.addItemDecoration( DividerItemDecoration( this, layoutManager.orientation ))

        iFirebaseLoadDone = this

        loadUserList()
        loadSearchData()
    }

    private fun startSearch( searchString: String ) {
        val query = FirebaseDatabase.getInstance().getReference( Common.USER_INFORMATION )
            .orderByChild( "email" )
            .startAt( searchString )

        val options = FirebaseRecyclerOptions.Builder< User >()
            .setQuery( query, User::class.java )
            .build()

        searchAdapter = object:FirebaseRecyclerAdapter<User, UserViewHolder>( options ) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
                var itemView = LayoutInflater.from( parent.context )
                    .inflate(R.layout.layout_user, parent, false)

                return UserViewHolder( itemView )
            }

            override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: User) {
                if( model.email.equals( Common.loggedUser.email )) {
                    holder.txt_user_email.text = StringBuilder( model.email!! ).append( " (me)");
                    holder.txt_user_email.setTypeface( holder.txt_user_email.typeface, Typeface.ITALIC )
                }
                else {
                    holder.txt_user_email.text = StringBuilder( model.email!! )
                }

                holder.setClick( object:IRecyclerItemClickListener{
                    override fun onItemClickListener(view: View, position: Int) {

                    }

                })

            }


        }
        searchAdapter!!.startListening()

        recycler_all_people.adapter = searchAdapter;
    }

    private fun loadSearchData() {
        //get all user
        val lstUserEmail = ArrayList< String >()
        val userList = FirebaseDatabase.getInstance().getReference( Common.USER_INFORMATION )

        userList.addListenerForSingleValueEvent( object:ValueEventListener {
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

    private fun loadUserList() {
        val query = FirebaseDatabase.getInstance().getReference( Common.USER_INFORMATION )

        val options = FirebaseRecyclerOptions.Builder< User >()
            .setQuery( query, User::class.java )
            .build()

        adapter = object:FirebaseRecyclerAdapter<User, UserViewHolder>( options ) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
                var itemView = LayoutInflater.from( parent.context )
                    .inflate(R.layout.layout_user, parent, false)

                return UserViewHolder( itemView )
            }

            override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: User) {
                if( model.email.equals( Common.loggedUser.email )) {
                    holder.txt_user_email.text = StringBuilder( model.email!! ).append( " (me)");
                    holder.txt_user_email.setTypeface( holder.txt_user_email.typeface, Typeface.ITALIC )
                }
                else {
                    holder.txt_user_email.text = StringBuilder( model.email!! )
                }

                holder.setClick( object:IRecyclerItemClickListener{
                    override fun onItemClickListener(view: View, position: Int) {

                    }

                })

            }


        }
        adapter!!.startListening()

        recycler_all_people.adapter = adapter;
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
}
