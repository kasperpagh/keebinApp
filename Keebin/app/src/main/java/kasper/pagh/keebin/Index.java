package kasper.pagh.keebin;

/**
 * Created by dino on 08-12-2016.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import CoffeeRest.rest.GetAllShops;
import CoffeeRest.rest.GetShopByEmail;
import entity.CoffeeShop;
import entity.LoyaltyCard;


public class Index extends Fragment implements AsyncResponse
{

    private DatabaseHandler dbh;


    public Index()
    {

    }

    View globalview;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        dbh = new DatabaseHandler(getActivity());
        GetAllShops allshops = new GetAllShops(getResources().getString(R.string.baseUrl), this, getActivity());
        allshops.execute();

        final View view = inflater.inflate(R.layout.index, container, false);


        globalview = view;

//
//ArrayList<Integer> listen = new ArrayList<Integer>();
//        listen.add(1);
//        listen.add(2);
//
//
//        for(int x = 0; x<listen.size(); x++) {
//
////            Integer example = R.drawable.riccos ;
//
//            String name =   dbh.getBrandbyId(listen.get(x)).getBrandName().toLowerCase();
//
//
//
//     Integer example = getContext().getResources().getIdentifier(name, "drawable", "kasper.pagh.keebin");
//
//
////            String strinsg = getString(R.string.hello_blank_fragment);
////
////            Toast.makeText(getContext(), strinsg, Toast.LENGTH_SHORT).show();
//
//
//
//
//
//
//
//
//
//
//
//            LinearLayout rl = (LinearLayout) view.findViewById(R.id.LinearLayout_Index);
//
//
//
//            ImageButton iv = new ImageButton(getContext());
//
//            iv.setImageDrawable(ContextCompat.getDrawable(getContext(), example));
//
//
//            LayoutParams lp = new LayoutParams(1000, 900);
//
//            lp.alignWithParent = true;
//
//            lp.setMargins(85, 30, 0, 0);
//
//            iv.setLayoutParams(lp);
//
//            final Integer value = example;
//            s.add(example);
//
//            iv.setOnClickListener( new View.OnClickListener() {
//
//
//                public void onClick(View v) {
//
//                    String name;
//
//
//
//                    getFragmentManager().beginTransaction().replace(R.id.fragment, Selectedshop.newInstance(1)).addToBackStack("A_B_TAG").commit();
//
//
//
//
//                }
//            });
//
//            if(++val == 5)
//                val = 1;
//
//
//            iv.setScaleType(ImageView.ScaleType.FIT_XY);
//
//            rl.addView(iv);
//
//
//        }
//
//        Log.d("I am here!" + s, "--");
//        Log.d("ssaa", "yooolo");


        return view;
    }

    public static Index newInstance()
    {
        Bundle args = new Bundle();
        Index fragment = new Index();
        fragment.setArguments(args);
        return fragment;
    }

    private Gson gson = new Gson();
    CoffeeShop[] shops;

    @Override
    public void processFinished(String output) {

       shops = gson.fromJson(output, CoffeeShop[].class);










    for (CoffeeShop shop : shops) {




        String name =   dbh.getBrandbyId(shop.getId()).getBrandName().toLowerCase();
     Integer example = getContext().getResources().getIdentifier(name, "drawable", "kasper.pagh.keebin");
            LinearLayout rl = (LinearLayout) globalview.findViewById(R.id.LinearLayout_Index);
            ImageButton iv = new ImageButton(getContext());
            iv.setImageDrawable(ContextCompat.getDrawable(getContext(), example));


            LayoutParams lp = new LayoutParams(1000, 900);
            lp.alignWithParent = true;
            lp.setMargins(85, 30, 0, 0);

            iv.setLayoutParams(lp);

            final Integer shopID = shop.getId();
            final String shopEMAIL = shop.getEmail();


            iv.setOnClickListener( new View.OnClickListener() {


                public void onClick(View v) {

                    String name;



                    getFragmentManager().beginTransaction().replace(R.id.fragment, Selectedshop.newInstance(shopID, shopEMAIL)).addToBackStack("A_B_TAG").commit();




                }
            });




            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            rl.addView(iv);


        }

        Log.d("ssaa", "yooolo");


    }
}
