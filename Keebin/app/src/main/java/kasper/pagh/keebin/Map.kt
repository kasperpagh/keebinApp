package kasper.pagh.keebin




import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Map : Fragment(), OnMapReadyCallback {

        companion object
    {
        @JvmStatic
        fun newInstance(): Map
        {
            var bundle: Bundle = Bundle()
            val fragment: Map = Map()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val MY_PERMISSIONS_REQUEST = 123

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View?
    {
        super.onCreate(savedInstanceState)
        var view: View = inflater!!.inflate(R.layout.map_layout, container, false)

        ActivityCompat.requestPermissions(activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),
                MY_PERMISSIONS_REQUEST)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view;
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
    // geo fix 12,511922 55,770535
    override fun onMapReady(googleMap: GoogleMap) {
        val cphbusiness = LatLng(55.770535, 12.511922)
        googleMap.addMarker(MarkerOptions().position(cphbusiness).title("Her"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cphbusiness))
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        googleMap.isMyLocationEnabled = true
    }
}






/**
 * Created by kaspe on 2016-12-14.
 */
//class Map : Fragment(), OnMapReadyCallback
//{
//
//    companion object
//    {
//        @JvmStatic
//        fun newInstance(): Map
//        {
//            var bundle: Bundle = Bundle()
//            val fragment: Map = Map()
//            fragment.arguments = bundle
//            return fragment
//        }
//    }
//
//
//    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View?
//    {
//        if (inflater != null)
//        {
//            var view: View = inflater.inflate(R.layout.map_layout, container, false)
//        }
//
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//
//        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
////        mapFragment.getMapAsync(this)
//
//        mapFragment.getMapAsync { activity }
//        return view
//    }
//
//    override fun onMapReady(googleMap: GoogleMap)
//    {
//        val cphbusiness = LatLng(55.770535, 12.511922)
//        googleMap.addMarker(MarkerOptions().position(cphbusiness).title("Her"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cphbusiness))
//
//
//        googleMap.isMyLocationEnabled = true
//    }
//
//
//}