package kasper.pagh.keebin;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import entity.CoffeeBrand;
import entity.LoyaltyCard;

/**
 * Created by mrlef on 12/1/2016.
 */

public class UsersLoyaltyCardsArrayAdapter extends ArrayAdapter<LoyaltyCard>{

    private Context context;
    private List<LoyaltyCard> loyaltyCards;


    public UsersLoyaltyCardsArrayAdapter(Context context, List<LoyaltyCard> loyaltyCards)
    {
        super(context, R.layout.loyaltycardrow, loyaltyCards);
        this.context = context;
        this.loyaltyCards = loyaltyCards;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View viewRow = inflater.inflate(R.layout.loyaltycardrow, parent, false);


//        LoyaltyCard cardForRow = new LoyaltyCard(new CoffeeBrand("baresso"), 3);

        String coffeeBrand = loyaltyCards.get(position).getNameOfBrand();
        String number = "" + loyaltyCards.get(position).getNumberofBeans();




        int cardBrand = context.getResources().getIdentifier(coffeeBrand, "drawable", "kasper.pagh.keebin");
        int cardNumber = context.getResources().getIdentifier(number, "drawable", "kasper.pagh.keebin");
        //Find billede dynamisk slut.

        ImageView viewLogo = (ImageView) viewRow.findViewById(R.id.logo);
        ImageView viewBean = (ImageView) viewRow.findViewById(R.id.beancounter);

        viewLogo.setImageDrawable(ContextCompat.getDrawable(context, cardBrand));
        viewBean.setImageDrawable(ContextCompat.getDrawable(context, cardNumber));

        return viewRow;

    }






}

