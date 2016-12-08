package kasper.pagh.keebin


import CoffeeRest.rest.GetAllShopsWithBrandName
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
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import entity.CoffeeShop

class Map : Fragment(), OnMapReadyCallback, AsyncResponse {



    val mapinstance = this
    lateinit var searchtext: android.widget.SearchView
    val gson: Gson = Gson()
    lateinit var gmap: GoogleMap
    var searchbool = false
    var searchspecificbool = false
    lateinit var bundle : Bundle


    companion object {
        @JvmStatic
        fun newInstance(shop: CoffeeShop): Map {
            var bundle: Bundle = Bundle()
            bundle.putDouble("lat", shop.latitude)
            bundle.putDouble("long", shop.longitude)
            bundle.putString("addr", shop.address)
            bundle.putString("email", shop.email)
            bundle.putString("name", shop.actualBrandName)
            bundle.putString("phone", shop.phone)
            val fragment: Map = Map()
            fragment.arguments = bundle
            return fragment
        }
        @JvmStatic
        fun newInstance(): Map {
            var bundle: Bundle = Bundle()
            val fragment: Map = Map()
            fragment.arguments = bundle
            return fragment
        }
    }





    private val MY_PERMISSIONS_REQUEST = 123

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        var view: View = inflater!!.inflate(R.layout.map_layout, container, false)

        bundle = arguments

        if(bundle.getDouble("long") != 0.0 && bundle.getDouble("lat") != 0.0) {

                Toast.makeText(activity, "" + bundle.getDouble("long"),
                        Toast.LENGTH_LONG).show();
            searchspecificbool = true
        }

        ActivityCompat.requestPermissions(activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),
                MY_PERMISSIONS_REQUEST)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val search = view.findViewById(R.id.searchbutton_maps) as ImageButton


         searchtext = view.findViewById(R.id.easyGGmofo) as android.widget.SearchView

        search.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View): Unit {

//                Toast.makeText(activity, "" + searchtext.query.toString(),
//                        Toast.LENGTH_LONG).show();

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

      if(searchbool) {
          coffeeArray.forEach {
              if (it.actualBrandName.contains(searchtext.query.toString(), ignoreCase = true)) {


                  Toast.makeText(activity, "" + it.actualBrandName,
                          Toast.LENGTH_LONG).show();

                  val cphbusiness = LatLng(it.latitude, it.longitude)
                  gmap.addMarker(MarkerOptions().position(cphbusiness).title(it.actualBrandName).snippet(it.address + ", " + it.email))
              }


          }
          searchbool = false
      }







    }




    override fun onMapReady(googleMap: GoogleMap) {

        gmap = googleMap

        if(searchspecificbool)
        {

            val lat  = bundle.getDouble("lat")
            val long = bundle.getDouble("long")
            val addr = bundle.getString("addr")
            val email = bundle.getString("email")
            val name = bundle.getString("name")
            val phone = bundle.getString("phone")

            val shoploc = LatLng(lat, long)
            gmap.addMarker(MarkerOptions().position(shoploc).title(name).snippet(addr + ", " + email + ", " +  phone))
            gmap.moveCamera(CameraUpdateFactory.newLatLng(shoploc))
            googleMap.setMinZoomPreference(11.0f)
            googleMap.setMaxZoomPreference(20.0f)

            searchspecificbool = false
        }
        else
        {

        val cphbusiness = LatLng(55.770535, 12.511922)
        googleMap.addMarker(MarkerOptions().position(cphbusiness).title("Her"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cphbusiness))
        googleMap.setMinZoomPreference(11.0f);
        googleMap.setMaxZoomPreference(20.0f);
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        googleMap.isMyLocationEnabled = true

        }
    }
}
