package kasper.pagh.keebin;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import CoffeeRest.rest.GetBrandByID;
import entity.CoffeeBrand;
import entity.LoyaltyCard;
import userReST.GetAllLoyaltyCards;


public class UsersLoyaltyCards extends Fragment implements AsyncResponse{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    List<LoyaltyCard> loyaltyList;

    private Gson gson = new Gson();


    public UsersLoyaltyCards() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static UsersLoyaltyCards newInstance() {
        UsersLoyaltyCards fragment = new UsersLoyaltyCards();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        loyaltyList = new ArrayList<>();


        GetAllLoyaltyCards g = new GetAllLoyaltyCards(getResources().getString(R.string.baseUrl), this);
        g.execute();


        final View view = inflater.inflate(R.layout.users_loyalty_cards, container, false);

        ListView listview = (ListView) view.findViewById(R.id.listOfUsersLoyaltyCards);

        //her laver vi et kald på den specifikke users loyaltycards og putter dem i en liste.
        // Den liste putter vi så ind i viewadapter.

//
//        LoyaltyCard cardForRow = new LoyaltyCard(new CoffeeBrand("baressologo"), "b3");
//        LoyaltyCard cardForRow2 = new LoyaltyCard(new CoffeeBrand("baressologo"), "b3");
//
//        loyaltyList.add(cardForRow);
//        loyaltyList.add(cardForRow2);

        listview.setAdapter(new UsersLoyaltyCardsArrayAdapter(getActivity(), loyaltyList));

       return view;
    }

    @Override
    public void processFinished(String output) {

        Log.d("this is output: ", "her kommer output: " + output);



        LoyaltyCard[] card = gson.fromJson(output, LoyaltyCard[].class);
        for (LoyaltyCard eachCard : card) {
                LoyaltyCard cardForRow = new LoyaltyCard(eachCard.getBrandId(), "b"+eachCard.getNumberOfCoffeesBought());
                Log.d("her er info: ", "yo2: " + cardForRow.getBrandId() + " and the number: " + cardForRow.getNumberofBeans());

        }








        //hent brandName og beancount(husk at parse to string) og lav nyt loyaltycard på de 2 variabler
        //add til list.

    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
