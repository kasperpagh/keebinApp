package kasper.pagh.keebin


import rest.coffeeRest.GetAllShopsWithBrandName
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import entity.CoffeeShop
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.location.LocationServices.FusedLocationApi


class MapFragment : Fragment(), OnMapReadyCallback, AsyncResponse, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    lateinit var mGoogleApiClient: GoogleApiClient
    val mapinstance = this
    lateinit var searchtext: android.widget.SearchView
    val gson: Gson = Gson()
    lateinit var gmap: GoogleMap
    var searchbool = false
    var searchspecificbool = false
    lateinit var bundle: Bundle
    private val MY_PERMISSIONS_REQUEST = 123

    companion object {
        @JvmStatic
        fun newInstance(shop: CoffeeShop): kasper.pagh.keebin.MapFragment
        {
            var bundle: Bundle = Bundle()
            bundle.putDouble("lat", shop.latitude)
            bundle.putDouble("long", shop.longitude)
            bundle.putString("addr", shop.address)
            bundle.putString("email", shop.email)
            bundle.putString("name", shop.actualBrandName)
            bundle.putString("phone", shop.phone)
            val fragment: kasper.pagh.keebin.MapFragment = kasper.pagh.keebin.MapFragment()
            fragment.arguments = bundle
            return fragment
        }

        @JvmStatic
        fun newInstance(): kasper.pagh.keebin.MapFragment
        {
            var bundle: Bundle = Bundle()
            val fragment: kasper.pagh.keebin.MapFragment = kasper.pagh.keebin.MapFragment()
            fragment.arguments = bundle
            return fragment
        }
    }




    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        var view: View = inflater!!.inflate(R.layout.map_layout, container, false)
        bundle = arguments
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            BuildApiGoogle()
            mGoogleApiClient.connect()
        }
        if (bundle.getDouble("long") != 0.0 && bundle.getDouble("lat") != 0.0) {

            Toast.makeText(activity, "" + bundle.getDouble("long"),
                    Toast.LENGTH_LONG).show();
            searchspecificbool = true
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val search = view.findViewById(R.id.searchbutton_maps) as ImageButton

        searchtext = view.findViewById(R.id.easyGGmofo) as android.widget.SearchView

        search.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View): Unit {
                    searchbool = true
                    val getAllShops = GetAllShopsWithBrandName(resources.getString(R.string.baseUrl), mapinstance, activity)
                    getAllShops.execute()
            }
        })
        return view;
    }


    override fun processFinished(output: String?) {
        gmap.clear()

        val coffeeArray = gson.fromJson(output, Array<CoffeeShop>::class.java)

        if (searchbool) {
            coffeeArray.forEach {
                if (it.actualBrandName.contains(searchtext.query.toString(), ignoreCase = true)) {


                    Toast.makeText(activity, "" + it.actualBrandName,
                            Toast.LENGTH_LONG).show();

                    val tempLatLng = LatLng(it.latitude, it.longitude)
                    gmap.addMarker(MarkerOptions().position(tempLatLng).title(it.actualBrandName).snippet(it.address + ", " + it.email))
                }


            }
            searchbool = false
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        gmap = googleMap
        gmap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        gmap.setTrafficEnabled(true);
        gmap.setIndoorEnabled(true);
        gmap.setBuildingsEnabled(true);
        gmap.getUiSettings().setZoomControlsEnabled(true);

        if (searchspecificbool) {
            val lat = bundle.getDouble("lat")
            val long = bundle.getDouble("long")
            val addr = bundle.getString("addr")
            val email = bundle.getString("email")
            val name = bundle.getString("name")
            val phone = bundle.getString("phone")
            val shoploc = LatLng(lat, long)
            gmap.addMarker(MarkerOptions().position(shoploc).title(name).snippet(addr + ", " + email + ", " + phone))
            gmap.moveCamera(CameraUpdateFactory.newLatLng(shoploc))
            gmap.setMinZoomPreference(11.0f)

            searchspecificbool = false
        } else {
            val cphbusiness = LatLng(55.770535, 12.511922)
            gmap.addMarker(MarkerOptions().position(cphbusiness).title("Her"))
            gmap.moveCamera(CameraUpdateFactory.newLatLng(cphbusiness))
            gmap.setMinZoomPreference(11.0f);
            gmap.isMyLocationEnabled = true
        }
    }

    fun BuildApiGoogle(): Unit {
        mGoogleApiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
    }

    override fun onConnectionSuspended(p0: Int) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnected(p0: Bundle?) {
        val mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient)
        if (mLastLocation == null) {
            println("kasper prøver")
            Toast.makeText(context, "123", Toast.LENGTH_LONG)
        } else {
            val latLng = LatLng(mLastLocation.latitude, mLastLocation.longitude)
            gmap.addMarker(MarkerOptions().position(latLng).title("vi ser vores loc"))
            gmap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            gmap.setMinZoomPreference(11.0f);
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
