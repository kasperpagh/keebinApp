package kasper.pagh.keebin;

/**
 * Created by dino on 08-12-2016.
 */
import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import CoffeeRest.rest.GetBrandByID;
import CoffeeRest.rest.GetShopByEmail;
import entity.CoffeeBrand;
import entity.CoffeeShop;
import entity.LoyaltyCard;

//public class Selectedshop extends Fragment implements AsyncResponse
//{
//
//    public Selectedshop()
//    {
//
//    }
//
//    View View;
//
//    TextView InfoBox;
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//    {
//
//
//
//        final View view = inflater.inflate(R.layout.selectedshop, container, false);
//        View = view;
//        Bundle bundle = getArguments();
//
//        int ID = bundle.getInt("shopIDs");
//
//        GetBrandName bn = new GetBrandName();
//        bn.getfromID(ID);
//
//        String mail = "bar@baresso.com"; // set this to what i get from selected page.
//
//
//
//        Integer example = R.drawable.riccos;
//
//
//        GetShopByEmail s = new GetShopByEmail(getResources().getString(R.string.baseUrl), mail, this);
//        s.execute();
//
//
//        //    rftk
//
//
//        ImageView shopPic = (ImageView) view.findViewById(R.id.ShopPicture);
//        TextView shopPicOverlay = (TextView) view.findViewById(R.id.ShopPictureOverlay);
//        InfoBox = (TextView) view.findViewById(R.id.InfoBox);
//
//
//        shopPic.setImageDrawable(ContextCompat.getDrawable(getContext(), example));
//
//        //     shopPicOverlay.setText("Måske bare fjern tekst?. " + val);
//        shopPicOverlay.setTextColor(Color.parseColor("#400D12"));
//
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        shopPic.setLayoutParams(lp);
//
////        InfoBox.setTextColor(Color.parseColor("#CDFFFF"));
//        InfoBox.setTextColor(Color.BLACK);
//
//
//
//
//
//
//        return view;
//    }
//
//
//
//
//    public static Selectedshop newInstance(Integer shopID)
//    {
//        Bundle args = new Bundle();
//        args.putInt("shopIDs", shopID);
//        Log.d("", "her er shop ID " + shopID);
//        Selectedshop fragment = new Selectedshop();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    CoffeeBrand brand;
//
//    public class GetBrandName implements AsyncResponse  {
//
//        public void getfromID(int ShopID)
//        {
//
//
//            GetBrandByID s = new GetBrandByID(getResources().getString(R.string.baseUrl), ShopID, this);
//            s.execute();
//        }
//
//        @Override
//        public void processFinished(String output) {
//
//            Log.d("her skulle vi finde bn ", "" + output);
//            String s = output.replaceAll("brandId", "brandName");
//
//            brand = gson.fromJson(s, CoffeeBrand.class);
//
//        }
//
//    }
//
//
//    private Gson gson = new Gson();
//
//    @Override
//    public void processFinished(String output) {
//
//
//
//
//
//        String s = output.replaceAll("brandId", "brandName"); // gør kun det her fordig cba at lave det om i serveren's respons eller hente det nye ned lol
//
//        CoffeeShop shop = gson.fromJson(s, CoffeeShop.class);
//
//        String phone = shop.getPhone().substring(0,2) + " " + shop.getPhone().substring(2,4)  + " " +  shop.getPhone().substring(4,6) + " " + shop.getPhone().substring(6,8);
//
//
//        Log.d("here is the output!. - ", s);
//        InfoBox.setText("Kontakt info \n" +
//                "\n" +
//                brand.getBrandName() + "\n" + "\n" +
//                shop.getAddress() + "\n" +
//                "\n" +
//                "Telefon: +45 " + phone +  "\n" +
//                "\n" +
//                "Mail: " + shop.getEmail() +  "(for alle henvendelser) \n");
//
//    }
//}