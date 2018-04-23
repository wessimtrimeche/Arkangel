package prx.test.kotlin.arkangel.module.adapter;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.hypertrack.lib.HyperTrackMapAdapter;
import com.hypertrack.lib.HyperTrackMapFragment;

/**
 * Created by wessim23 on 3/23/18.
 */
//Extend HyperTrackMapAdapter for customizing UI elements in the map view
public class MyMapAdapter extends HyperTrackMapAdapter {
    public MyMapAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public boolean showPlaceSelectorView() {
        return true;
    }
    @Override
    public boolean enableLiveLocationSharingView() {
        return true;
    }
    @Override
    public CameraUpdate getMapFragmentInitialState(HyperTrackMapFragment
                                                           hyperTrackMapFragment) {


//        if (SharedPreferenceManager.getLastKnownLocation() != null) {
//            LatLng latLng = new LatLng(35.86065640000001, 10.615435300000001);
//            return CameraUpdateFactory.newLatLngZoom(latLng, 15.0f);
//        }
        return super.getMapFragmentInitialState(hyperTrackMapFragment);
    }
}