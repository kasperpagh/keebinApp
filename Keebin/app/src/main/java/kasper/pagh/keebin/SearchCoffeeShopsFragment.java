package kasper.pagh.keebin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rest.coffeeRest.GetAllShopsWithBrandName;
import adapters.CoffeeShopArrayAdapter;
import entity.CoffeeShop;

/**
 * Created by kaspe on 2016-11-26.
 */

public class SearchCoffeeShopsFragment extends Fragment implements AsyncResponse
{
    private Gson gson = new Gson();
    CoffeeShopArrayAdapter adapter;

    private List<CoffeeShop> coffeeList = new ArrayList<>();


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

        Button searchButton = (Button) view.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText searchField = (EditText) view.findViewById(R.id.SearchCoffeeShop);
                String searchString = searchField.getText().toString();
                Log.d("klik m. strengen", searchString);

                List<CoffeeShop> resultList = new ArrayList<CoffeeShop>();
                for (int i = 0; i < coffeeList.size(); i++)
                {
                    if (coffeeList.get(i).getActualBrandName().equalsIgnoreCase(searchString) || coffeeList.get(i).getAddress().equalsIgnoreCase(searchString) || coffeeList.get(i).getEmail().equalsIgnoreCase(searchString))
                    {
                        resultList.add(coffeeList.get(i));
                    }

                }
                if (searchString.equalsIgnoreCase(""))
                {
                    adapter.clear();
                    adapter.addAll(coffeeList);
                    adapter.notifyDataSetChanged();
                } else
                {
                    adapter.clear();
                    adapter.addAll(resultList);
                    adapter.notifyDataSetChanged();
                }
            }
        });



        GetAllShopsWithBrandName getAllShops = new GetAllShopsWithBrandName(getResources().getString(R.string.baseUrl), this, getActivity());

        getAllShops.execute();


        adapter = new CoffeeShopArrayAdapter(getActivity(), coffeeList);

        ListView listView = (ListView) view.findViewById(R.id.searchList);
        listView.setAdapter(adapter);


        return view;
    }


    public void searchTester(View view)
    {
        ListView listView = (ListView) view.findViewById(R.id.searchList);
        listView.setAdapter(new CoffeeShopArrayAdapter(getActivity(), coffeeList));
    }

    @Override
    public void processFinished(String output)
    {
        CoffeeShop[] coffeeArray = gson.fromJson(output, CoffeeShop[].class);
        coffeeList = (Arrays.asList(coffeeArray));
        adapter.addAll(coffeeList);
        adapter.notifyDataSetChanged();

    }

}
