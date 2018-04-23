package prx.test.kotlin.arkangel.module.home.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hypertrack.lib.HyperTrackMapFragment
import com.hypertrack.lib.MapFragmentCallback
import com.hypertrack.lib.models.Place
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.module.adapter.MyMapAdapter

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val mapFragmentCallback = object: MapFragmentCallback() {
        override fun onExpectedPlaceSelected(expectedPlace: Place) {
            super.onExpectedPlaceSelected(expectedPlace)
            if (expectedPlace != null)
            {
                // Use this place to createAndAssignAction for current userId
            }
        }
    }
    lateinit var htMapFragment: HyperTrackMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//                .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)


        // Initialize HyperTrackMapFragment in Activity’s layout file to getMapAsync
        htMapFragment = getSupportFragmentManager().findFragmentById(R.id.htMapfragment) as HyperTrackMapFragment
        htMapFragment.setHTMapAdapter(MyMapAdapter(this@MapsActivity))
        htMapFragment.setMapFragmentCallback(mapFragmentCallback);


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
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
