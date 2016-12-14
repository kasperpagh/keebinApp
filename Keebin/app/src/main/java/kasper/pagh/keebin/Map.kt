package kasper.pagh.keebin

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


/**
 * Created by kaspe on 2016-12-14.
 */
class Map : Fragment(), OnMapReadyCallback
{


    lateinit var mMapView: MapView
    var mapView: MapView? = null
    lateinit var googleMap: GoogleMap

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


    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View?
    {

        val rootView = inflater!!.inflate(R.layout.map_layout, container, false)

        mMapView = rootView.findViewById(R.id.mapFragment) as MapView
        mMapView.onCreate(savedInstanceState)

        mMapView.onResume() // needed to get the map to display immediately

        try
        {
            MapsInitializer.initialize(activity.applicationContext)
        }
        catch (e: Exception)
        {
            e.printStackTrace()
        }

        mMapView.getMapAsync { this }
//        mMapView.getMapAsync(OnMapReadyCallback { mMap ->
//            googleMap = mMap
//
//            if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//            {
//                return@OnMapReadyCallback
//            }
//            googleMap.isMyLocationEnabled = true
//
//            Log.d("ligefør latLng", " lige før sidney")
//            val sydney = LatLng(-34.0, 151.0)
//            Log.d("her er john ", sydney.toString())
//
//            googleMap.addMarker(MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"))
//
////            val cameraPosition = CameraPosition.Builder().target(sydney).zoom(12f).build()
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
////            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
//        })

        return rootView
    }


    override fun onMapReady(googleMap: GoogleMap)
    {
        val cphbusiness = LatLng(55.770535, 12.511922)
        googleMap.addMarker(MarkerOptions().position(cphbusiness).title("Her"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cphbusiness))
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return
        }

        googleMap.isMyLocationEnabled = true
    }


    override fun onResume()
    {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause()
    {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy()
    {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onLowMemory()
    {
        super.onLowMemory()
        mMapView.onLowMemory()
    }

}