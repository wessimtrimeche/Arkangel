package prx.test.kotlin.arkangel.module.home.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hypertrack.lib.HyperTrackMapFragment
import com.hypertrack.lib.MapFragmentCallback
import com.hypertrack.lib.internal.consumer.view.Placeline.PlacelineFragment
import com.hypertrack.lib.models.Place
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.module.adapter.MyMapAdapter
import prx.test.kotlin.arkangel.module.profile.model.Child
import prx.test.kotlin.arkangel.module.profile.model.User

class Main2Activity : AppCompatActivity() {
    private val mapFragmentCallback = object: MapFragmentCallback() {
        override fun onExpectedPlaceSelected(expectedPlace: Place) {
            super.onExpectedPlaceSelected(expectedPlace)
            if (expectedPlace != null)
            {
                // Use this place to createAndAssignAction for current userId
            }
        }
    }
    lateinit var firebaseUser: FirebaseUser
    lateinit var mAuth: FirebaseAuth

    lateinit var htMapFragment: HyperTrackMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Initialize HyperTrackMapFragment in Activity’s layout file to getMapAsync
        htMapFragment = getSupportFragmentManager().findFragmentById(R.id.htMapfragment) as HyperTrackMapFragment
        htMapFragment.setHTMapAdapter(MyMapAdapter(this@Main2Activity))
        htMapFragment.setMapFragmentCallback(mapFragmentCallback);


        mAuth = FirebaseAuth.getInstance()
        firebaseUser = mAuth.getCurrentUser()!!

        var displayName = firebaseUser.displayName

        Log.d("pppppppppp",FirebaseDatabase.getInstance().getReference().child("users/"+displayName+"/childsList/").toString())
        val pos: LatLng ;
        FirebaseDatabase.getInstance().getReference().child("users/"+displayName+"/childsList/")
                .addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.getChildren())
                        {
                            val child = snapshot.getValue(Child::class.java)


                            val pos = LatLng(child!!.latitude,child!!.longitude)

//                            htMapFragment.addMarkerPulse(pos,displayName)

                            val markerOption = MarkerOptions().position(LatLng(pos.latitude, pos.longitude)).title("child1")

                            htMapFragment.addCustomMarker(markerOption)

//                            Log.d("pppppppppp",child?.name.toString())
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })
//        htMapFragment.addMarkerPulse()


    }
}
