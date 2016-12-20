package kasper.pagh.keebin;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.LoyaltyCard;
import userReST.GetAllLoyaltyCards;


public class UsersLoyaltyCards extends Fragment implements AsyncResponse{



    List<LoyaltyCard> loyaltyList;
    private Gson gson = new Gson();
    UsersLoyaltyCardsArrayAdapter adapter;

    public UsersLoyaltyCards() {
    }

    public static UsersLoyaltyCards newInstance() {
        UsersLoyaltyCards fragment = new UsersLoyaltyCards();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        loyaltyList = new ArrayList<>();



        GetAllLoyaltyCards g = new GetAllLoyaltyCards( this, getActivity());

        g.execute();


        final View view = inflater.inflate(R.layout.users_loyalty_cards, container, false);

        ListView listview = (ListView) view.findViewById(R.id.listOfUsersLoyaltyCards);



        adapter = new UsersLoyaltyCardsArrayAdapter(getActivity(), loyaltyList);

        listview.setAdapter(adapter);

       return view;
    }

    @Override
    public void processFinished(String output) {

        Log.d("this is output: ", "her kommer output fra UsersLoyaltyCards: " + output);
        DatabaseHandler dbh = new DatabaseHandler(getContext());



        LoyaltyCard[] card = gson.fromJson(output, LoyaltyCard[].class);
        for (LoyaltyCard eachCard : card) {
            Log.d("her er brandName", " "+ eachCard.getBrandName());
                LoyaltyCard cardForRow = new LoyaltyCard(dbh.getBrandbyServerId(eachCard.getBrandName()).getBrandName().toLowerCase(), "b"+eachCard.getNumberOfCoffeesBought());
                Log.d("her er cardForRow: ", " " + cardForRow.getNameOfBrand() + " and the number: " + cardForRow.getNumberofBeans());
                loyaltyList.add(cardForRow);
        }
        adapter.notifyDataSetChanged();


    }

}
