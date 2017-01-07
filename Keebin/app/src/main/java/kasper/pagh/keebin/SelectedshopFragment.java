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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import rest.coffeeRest.GetBrandByID;
import rest.coffeeRest.GetShopByEmail;
import DB.DatabaseHandler;
import entity.CoffeeBrand;
import entity.CoffeeShop;

public class SelectedshopFragment extends Fragment implements AsyncResponse
{

    public SelectedshopFragment()
    {

    }

    DatabaseHandler dbh;

    View View;

    TextView InfoBox;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {


        final View view = inflater.inflate(R.layout.selectedshop, container, false);
        View = view;
        Bundle bundle = getArguments();


        int ID = bundle.getInt("shopIDs");
        String email = bundle.getString("shopMail");

        GetBrandName bn = new GetBrandName();
        bn.getfromID(ID);



//        Integer example = R.drawable.riccos;


        GetShopByEmail s = new GetShopByEmail(getResources().getString(R.string.baseUrl), email, this, getActivity());
        s.execute();


        ImageView shopPic = (ImageView) view.findViewById(R.id.ShopPicture);
        TextView shopPicOverlay = (TextView) view.findViewById(R.id.ShopPictureOverlay);
        InfoBox = (TextView) view.findViewById(R.id.InfoBox);


        dbh = new DatabaseHandler(getActivity());
        String name = dbh.getBrandbyId(ID).getBrandName().toLowerCase();

        Integer example = getContext().getResources().getIdentifier(name, "drawable", "kasper.pagh.keebin");

        shopPic.setImageDrawable(ContextCompat.getDrawable(getContext(), example));

        shopPicOverlay.setText(""); // writes over the picture!
        shopPicOverlay.setTextColor(Color.parseColor("#400D12"));

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        shopPic.setLayoutParams(lp);

//        InfoBox.setTextColor(Color.parseColor("#CDFFFF"));
        InfoBox.setTextColor(Color.BLACK);


        return view;
    }

    public static SelectedshopFragment newInstance(Integer shopID, String shopMail)
    {
        Bundle args = new Bundle();
        args.putInt("shopIDs", shopID);
        args.putString("shopMail", shopMail);
        Log.d("", "her er shop ID " + shopID);
        SelectedshopFragment fragment = new SelectedshopFragment();
        fragment.setArguments(args);
        return fragment;
    }

    CoffeeBrand brand;

    public class GetBrandName implements AsyncResponse
    {

        public void getfromID(int ShopID)
        {


            GetBrandByID s = new GetBrandByID(getResources().getString(R.string.baseUrl), ShopID, this, getActivity());
            s.execute();
        }

        @Override
        public void processFinished(String output)
        {

            Log.d("her skulle vi finde bn ", "" + output);
            String s = output.replaceAll("brandId", "brandName");

            brand = gson.fromJson(s, CoffeeBrand.class);

        }

    }


    private Gson gson = new Gson();

    @Override
    public void processFinished(String output)
    {


        String s = output.replaceAll("brandId", "brandName"); // gør kun det her fordig cba at lave det om i serveren's respons eller hente det nye ned lol

        CoffeeShop shop = gson.fromJson(s, CoffeeShop.class);


        String phone = shop.getPhone();
        if(shop.getPhone().length() == 8) // if the shop length is equal to 8 characters, split it up.
            phone = shop.getPhone().substring(0,2) + " " + shop.getPhone().substring(2,4)  + " " +  shop.getPhone().substring(4,6) + " " + shop.getPhone().substring(6,8);



        Log.d("here is the output!. - ", s);
        InfoBox.setText("Kontakt info \n" +
                "\n" +
                brand.getBrandName() + "\n" + "\n" +
                shop.getAddress() + "\n" +
                "\n" +
                "Telefon: +45 " + phone + "\n" +
                "\n" +
                "Mail: " + shop.getEmail() + "(for alle henvendelser) \n");

    }
}