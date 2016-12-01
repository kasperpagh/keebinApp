package kasper.pagh.keebin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;


import java.util.ArrayList;
import java.util.List;

import CoffeeRest.rest.GetAllShops;
import entity.CoffeeShop;

/**
 * Created by kaspe on 2016-11-26.
 */

public class SearchCoffeeShopsFragment extends Fragment implements AsyncResponse
{
    private TextView textView;

    private List<CoffeeShop> coffeeList = new ArrayList<CoffeeShop>();
    private List<CoffeeShop> coffeeList2 = new ArrayList<CoffeeShop>();

    public SearchCoffeeShopsFragment()
    {

    }

    public static SearchCoffeeShopsFragment newInstance()
    {
        Bundle args = new Bundle();
        SearchCoffeeShopsFragment fragment = new SearchCoffeeShopsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d("her er curUsr ", MainActivity.currentUser.toString());
        final View view = inflater.inflate(R.layout.search_coffee_shops_fragment, container, false);

        CoffeeShop coffee1 = new CoffeeShop("bob@bob.dk", "llamavej 12", "12345678", 1, "bubbers kaffebutik");
        CoffeeShop coffee2 = new CoffeeShop("bob@bob.dk", "llamavej 13", "12345678", 1, "jimbos kaffebutik");
        CoffeeShop coffee3 = new CoffeeShop("bob@bob.dk", "llamavej 14", "12345678", 1, "johnnys kaffebutik");
        CoffeeShop coffee4 = new CoffeeShop("bob@bob.dk", "llamavej 15", "12345678", 1, "arnes kaffebutik");
        CoffeeShop coffee5 = new CoffeeShop("bob@bob.dk", "llamavej 16", "12345678", 1, "llamas kaffebutik");
        CoffeeShop coffee6 = new CoffeeShop("bob@bob.dk", "llamavej 17", "12345678", 1, "bobsons kaffebutik");

        coffeeList.add(coffee1);
        coffeeList.add(coffee2);
        coffeeList.add(coffee3);
        coffeeList.add(coffee4);
        coffeeList.add(coffee5);
        coffeeList.add(coffee6);

        coffeeList2.add(coffee4);
        coffeeList2.add(coffee5);
        coffeeList2.add(coffee6);
//        textView = (TextView) view.findViewById(R.id.resText);
//        GetAllShops getAllShops = new GetAllShops(getResources().getString(R.string.baseUrl),this);
//        getAllShops.execute();
        ListView listView = (ListView) view.findViewById(R.id.searchList);
        listView.setAdapter(new CoffeeShopArrayAdapter(getActivity(), coffeeList));


        return view;
    }

//    public View


    public void searchTester(View view)
    {
        ListView listView = (ListView) view.findViewById(R.id.searchList);
        listView.setAdapter(new CoffeeShopArrayAdapter(getActivity(), coffeeList));
    }

    @Override
    public void processFinished(String output)
    {
        textView.setText(output);
    }
}
