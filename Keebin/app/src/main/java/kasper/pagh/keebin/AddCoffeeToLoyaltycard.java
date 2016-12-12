package kasper.pagh.keebin;

import android.app.Activity;

import android.os.Bundle;

import android.support.v4.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import entity.User;
import userReST.AddCoffee;

/**
 * Created by pelle on 12/12/2016.
 */

public class AddCoffeeToLoyaltycard extends Fragment implements AsyncResponse
{
    EditText coffeeCode;
    EditText numberOfCoffeesBought;

    public static AddCoffeeToLoyaltycard newInstance()
    {
        Bundle args = new Bundle();
        AddCoffeeToLoyaltycard fragment = new AddCoffeeToLoyaltycard();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.coffee_add, container, false);
        coffeeCode = (EditText) view.findViewById(R.id.coffeeCode);
        numberOfCoffeesBought = (EditText) view.findViewById(R.id.numberOfCoffeesBought);
        Button button = (Button) view.findViewById(R.id.addCoffee);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                AddCoffee addCoffee = new AddCoffee(getResources().getString(R.string.baseUrl), MainActivity.currentUser, coffeeCode.getText().toString(), numberOfCoffeesBought.getText().toString(), AddCoffeeToLoyaltycard.this, getContext());
                addCoffee.execute();
            }
        });


        return view;

    }


    @Override
    public void processFinished(String output)
    {
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment, UsersLoyaltyCards.newInstance()).commit();

    }
}