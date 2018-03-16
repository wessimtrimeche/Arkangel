package prx.test.kotlin.arkangel.module.home.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.NonNull
import android.util.Log
import android.widget.Toast
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
import com.hypertrack.lib.HyperTrack.getOrCreateUser
import com.hypertrack.lib.callbacks.HyperTrackCallback
import com.hypertrack.lib.models.ErrorResponse
import com.hypertrack.lib.models.SuccessResponse
import com.hypertrack.lib.models.UserParams
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.common.utils.TrackGPS

class HomeActivity : AppCompatActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnected(p0: Bundle?) {
        longitude = gps.getLongitude()
        latitude = gps.getLatitude()
    }

    private lateinit var mMap: GoogleMap
    internal var longitude: Double = 0.toDouble()
    internal var latitude: Double = 0.toDouble()
    lateinit var gps: TrackGPS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        gps = TrackGPS(applicationContext)

        var mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()






//
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
//            val alertDialog = AlertDialog.Builder(this@HomeActivity)
//
//
//            alertDialog.setTitle("GPS Not Enabled")
//
//            alertDialog.setMessage("Do you want to turn On your GPS")
//
//
//            alertDialog.setPositiveButton("Yes") { dialog, which ->
//                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                this@HomeActivity.startActivity(intent)
//            }
//
//
//            alertDialog.setNegativeButton("No") { dialog, which -> dialog.cancel() }
//
//
//            alertDialog.show()
//
//        }
//

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val htl = LatLng(35.86065640000001, 10.615435300000001)

        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(htl).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(htl, 12F))
    }
}
