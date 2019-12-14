package com.project.challengemine

import android.content.Intent
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
import com.google.gson.Gson
import com.mancj.materialsearchbar.MaterialSearchBar
import com.project.challengemine.Interface.IFirebaseLoadDone
import com.project.challengemine.Interface.IRecyclerItemClickListener
import com.project.challengemine.Model.*
import com.project.challengemine.Util.Common
import com.project.challengemine.ViewHolder.UserViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_all_people.*

class AllPeopleActivity : AppCompatActivity(), IFirebaseLoadDone {

    var adapter:FirebaseRecyclerAdapter<User, UserViewHolder>?=null
    var searchAdapter:FirebaseRecyclerAdapter<User, UserViewHolder>?=null


    lateinit var iFirebaseLoadDone: IFirebaseLoadDone
    var suggestedList:List< String > = ArrayList()

    val compositeDisposable = CompositeDisposable()

    lateinit var tempDuelType : String
    lateinit var tempDistanceDuelObj: DistanceDuel
    lateinit var tempTimeDuelObj: TimeDuel
    companion object {
        private val MY_REQUEST_CODE = 1221
    }
    
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
                        showDialogRequest( model )
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
                        showDialogRequest( model )
                    }

                })

            }


        }
        adapter!!.startListening()

        recycler_all_people.adapter = adapter;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if( requestCode == MY_REQUEST_CODE) {
            val duelType = data?.getStringExtra("duelType");
            val time = data?.getStringExtra("time");
            val distance = data?.getStringExtra("distance");
            val model = Gson().fromJson( data?.getStringExtra("model"), User::class.java )

            if( duelType != null ) {
                if( duelType == Common.DUEL_TYPE_TIME ) {
                    val intent = Intent(this, TimeDuelActivity::class.java)
                    intent.putExtra(Common.DUEL_EXTRA_INTENT, Gson().toJson(TimeDuel(Common.loggedUser,model,time!!.toFloat())))
                    startActivity(intent);
                }
                else {
                    val intent = Intent(this, DistanceDuelActivity::class.java)
                    intent.putExtra(Common.DUEL_EXTRA_INTENT,
                        Gson().toJson(DistanceDuel(Common.loggedUser,model,distance!!.toFloat())))
                    startActivity(intent);
                }

                tempDuelType = duelType
                val acceptList = FirebaseDatabase.getInstance().getReference( Common.USER_INFORMATION )
                .child( Common.loggedUser.uid!! )
                .child( Common.ACCEPT_LIST )

                //check if user is not already in duel
                acceptList.orderByKey().equalTo( model.uid )
                    .addListenerForSingleValueEvent( object:ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {

                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            //not in duel
                            if (p0.value == null)  {
                                if( duelType == Common.DUEL_TYPE_DISTANCE )
                                    sendDuelRequest( DistanceDuel(Common.loggedUser,model,distance!!.toFloat()) )
                                else
                                    sendDuelRequest( TimeDuel(Common.loggedUser,model,time!!.toFloat()))
                            }
                            else
                                Toast.makeText( this@AllPeopleActivity,
                                    "You are already in duel", Toast.LENGTH_LONG).show()
                        }
                    })

            }
        }
        loadUserList()
    }
    
    private fun showDialogRequest(model: User) {
        val intent = Intent(this, ChooseDuel::class.java)
        intent.putExtra( "model", Gson().toJson( model ))
        startActivityForResult( intent, MY_REQUEST_CODE);

//        var alertDialog = AlertDialog.Builder( this, R.style.MyRequestDialog)
//        alertDialog.setTitle( "Duel Request" )
//        alertDialog.setMessage( StringBuilder("Do You want to send duel request to ")
//            .append( model.name )
//            .append( "( ")
//            .append( model.email)
//            .append( " )").toString())
//        alertDialog.setIcon( R.drawable.ic_challenge_mine_icon)
//        alertDialog.setNegativeButton( "Cancel", { dialogInterface, _ -> dialogInterface.dismiss() })
//
//        alertDialog.setPositiveButton( "Send") { _ , _->
//            val acceptList = FirebaseDatabase.getInstance().getReference( Common.USER_INFORMATION )
//                .child( Common.loggedUser.uid!! )
//                .child( Common.ACCEPT_LIST )
//
//            //check if user is not already in duel
//            acceptList.orderByKey().equalTo( model.uid )
//                .addListenerForSingleValueEvent( object:ValueEventListener {
//                    override fun onCancelled(p0: DatabaseError) {
//
//                    }
//
//                    override fun onDataChange(p0: DataSnapshot) {
//                        if (p0.value == null) //not in duel
//                            sendDuelRequest( model )
//                        else
//                            Toast.makeText( this@AllPeopleActivity,
//                                "You are already in duel", Toast.LENGTH_LONG).show()
//                    }
//                })
//        }
//
//        alertDialog.show()
    }

    private fun sendDuelRequest(model: Duel) {
        //get token to send duel request

        val tokens = FirebaseDatabase.getInstance().getReference( Common.TOKENS )
        tokens.orderByKey().equalTo( model.defender?.uid ).addListenerForSingleValueEvent( object:ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if( p0.value == null ) // tokens not available
                    Toast.makeText( this@AllPeopleActivity,
                        "Tokens not available", Toast.LENGTH_SHORT).show()
                else {
                    val request = Request()

                    val dataSend = HashMap< String, String >()
                    dataSend[ Common.DUEL_REQUEST ] = Gson().toJson( model )
                    dataSend[ Common.DUEL_TYPE ] = model.type


                    request.to = p0.child( model.defender!!.uid!! ).getValue( String::class.java )!!
                    request.data = dataSend

                    compositeDisposable.add( Common.ifcmService.sendDuelRequestToUser( request )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread())
                        .subscribe( { t: MyResponse? ->
                            if( t!!.success == 1 ) {
                                Toast.makeText(
                                    this@AllPeopleActivity,
                                    "Request sent", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }, { t: Throwable? ->
                                Toast.makeText( this@AllPeopleActivity,
                                    t!!.message, Toast.LENGTH_SHORT).show()
                        }))
                }
            }
        })
    }

    override fun onStop() {
        if( adapter != null)
            adapter!!.stopListening()
        if( searchAdapter != null)
            searchAdapter!!.stopListening()

        compositeDisposable.clear()
        super.onStop()
    }

    override fun onFirebaseLoadUserDone(lstEmail: List<String>) {
        material_search_bar.lastSuggestions = lstEmail
    }

    override fun onFirebaseLoadUserFailed(message: String) {
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show()
    }
}
