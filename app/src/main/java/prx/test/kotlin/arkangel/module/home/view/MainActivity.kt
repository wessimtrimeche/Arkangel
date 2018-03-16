package prx.test.kotlin.arkangel.module.home.view

import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hypertrack.lib.HyperTrack
import com.hypertrack.lib.callbacks.HyperTrackCallback
import com.hypertrack.lib.models.ErrorResponse
import com.hypertrack.lib.models.SuccessResponse
import com.hypertrack.lib.models.UserParams
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.common.utils.TrackGPS
import prx.test.kotlin.arkangel.module.authentication.view.LoginActivity
import prx.test.kotlin.arkangel.module.childProfile.view.AddChildActivity
import prx.test.kotlin.arkangel.module.profile.view.EditProfileActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnected(p0: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private lateinit var mMap: GoogleMap
    internal var longitude: Double = 0.toDouble()
    internal var latitude: Double = 0.toDouble()
    lateinit var gps: TrackGPS
    lateinit var firebaseUser: FirebaseUser
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        mAuth = FirebaseAuth.getInstance()
        firebaseUser = mAuth.getCurrentUser()!!



        val header = nav_view_main.getHeaderView(0)
        val userMail :TextView = header.findViewById (R.id.user_email)
        val userName :TextView = header.findViewById (R.id.username)
        val imageView :CircleImageView = header.findViewById (R.id.imageView)




        userMail.setText(firebaseUser.email)
        userName.setText(firebaseUser.displayName)
        if (firebaseUser.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(firebaseUser!!.getPhotoUrl()!!.toString())
                    .into(imageView)
        }

        imageView.setOnClickListener(View.OnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))

        })



        gps = TrackGPS(applicationContext)

        var mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        HyperTrack.initialize(this,"pk_a1ec724ffcfc62d04e8a11344ec116426d99dd4e")


        val firebaseUser: FirebaseUser?= FirebaseAuth.getInstance().currentUser

        val userParams = UserParams()
                .setName(firebaseUser?.displayName)
                .setPhone(firebaseUser?.phoneNumber)
                .setPhoto(firebaseUser?.photoUrl.toString())
                .setLookupId(firebaseUser?.phoneNumber)

        HyperTrack.getOrCreateUser(userParams, object: HyperTrackCallback() {
            override fun onSuccess(@NonNull successResponse: SuccessResponse) {
                // Handle success on getOrCreate user
                HyperTrack.startTracking()
            }
            override fun onError(@NonNull errorResponse: ErrorResponse) {
                // Handle error on getOrCreate user
                Toast.makeText(applicationContext, errorResponse.getErrorMessage(), Toast.LENGTH_SHORT).show()
            }
        })


        addChildBtn.setOnClickListener(View.OnClickListener {
            finish()
            startActivity(Intent(this, AddChildActivity::class.java))

        })



//        if (gps.canGetLocation) {
////            val intent = Intent(applicationContext, HomeActivity::class.java)
////            startActivityForResult(intent, 1)
//
//
//
//            Log.d("location",""+longitude+" and "+latitude )
//
//        } else if (!gps.canGetLocation) {
//
//            Toast.makeText(applicationContext, "No Service Provider Available", Toast.LENGTH_SHORT).show()
//
//            val alertDialog = AlertDialog.Builder(this@MainActivity)
//
//
//            alertDialog.setTitle("GPS Not Enabled")
//
//            alertDialog.setMessage("Do you want to turn On your GPS")
//
//
//            alertDialog.setPositiveButton("Yes") { dialog, which ->
//                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                this@MainActivity.startActivity(intent)
//            }
//
//
//            alertDialog.setNegativeButton("No") { dialog, which -> dialog.cancel() }
//
//
//            alertDialog.show()
//
//        }




        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view_main.setNavigationItemSelectedListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        HyperTrack.stopTracking()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                finish()
                startActivity(Intent(this, LoginActivity::class.java))

                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        when (item.itemId) {
            R.id.user_profile -> {
                finish()
                startActivity(Intent(this, EditProfileActivity::class.java))
            }
            R.id.nav_manage -> {

            }
            R.id.help -> {

            }
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                finish()
                startActivity(Intent(this, LoginActivity::class.java))

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val htl = LatLng(35.86065640000001, 10.615435300000001)

        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(htl).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(htl, 12F))

    }

}
