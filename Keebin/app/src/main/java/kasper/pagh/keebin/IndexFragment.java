package kasper.pagh.keebin;

/**
 * Created by dino on 08-12-2016.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.google.gson.Gson;

import rest.coffeeRest.GetAllShops;
import DB.DatabaseHandler;
import entity.CoffeeShop;


public class IndexFragment extends Fragment implements AsyncResponse
{
    private DatabaseHandler dbh;

    public IndexFragment()
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
        return view;
    }

    public static IndexFragment newInstance()
    {
        Bundle args = new Bundle();
        IndexFragment fragment = new IndexFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Gson gson = new Gson();
    CoffeeShop[] shops;

    @Override
    public void processFinished(String output)
    {

        shops = gson.fromJson(output, CoffeeShop[].class);

        for (CoffeeShop shop : shops)
        {
            String name = dbh.getBrandbyId(shop.getId()).getBrandName().toLowerCase();
            Integer example = getContext().getResources().getIdentifier(name, "drawable", "kasper.pagh.keebin");
            LinearLayout rl = (LinearLayout) globalview.findViewById(R.id.LinearLayout_Index);
            ImageButton iv = new ImageButton(getContext());
            iv.setImageDrawable(ContextCompat.getDrawable(getContext(), example));


            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.setMargins(0, 30, 0, 0);

            iv.setLayoutParams(lp);

            final Integer shopID = shop.getId();
            final String shopEMAIL = shop.getEmail();

            iv.setBackgroundColor(Color.WHITE);


            iv.setOnClickListener(new View.OnClickListener()
            {


                public void onClick(View v)
                {

                    String name;

                    getFragmentManager().beginTransaction().replace(R.id.fragment, SelectedshopFragment.newInstance(shopID, shopEMAIL)).addToBackStack("A_B_TAG").commit();
                    
                }
            });




            rl.addView(iv);


        }

        Log.d("ssaa", "yooolo");


    }
}
