package kasper.pagh.keebin


import CoffeeRest.rest.GetAllShopsWithBrandName
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.FragmentManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.SearchEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.common.api.GoogleApiClient
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
    var initbool = true

    companion object {
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

        if(initbool)
        {



            initbool = false
        }





    }




    override fun onMapReady(googleMap: GoogleMap) {

        gmap = googleMap
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





