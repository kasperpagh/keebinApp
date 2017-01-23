package kasper.pagh.keebin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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

        final EditText searchField = (EditText) view.findViewById(R.id.SearchCoffeeShop);

        searchField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                String searchString = charSequence.toString();
                List<CoffeeShop> resultList = new ArrayList<CoffeeShop>();
                for (int iterator = 0; iterator < coffeeList.size(); iterator++)
                {
                    if (coffeeList.get(iterator).getActualBrandName().toLowerCase().contains(searchString.toLowerCase()) || coffeeList.get(iterator).getAddress().toLowerCase().contains(searchString.toLowerCase()) || coffeeList.get(iterator).getEmail().toLowerCase().contains(searchString.toLowerCase()))
                    {
                        resultList.add(coffeeList.get(iterator));
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

            @Override
            public void afterTextChanged(Editable editable)
            {

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