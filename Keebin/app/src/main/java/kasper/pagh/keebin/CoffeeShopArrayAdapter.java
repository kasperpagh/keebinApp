package kasper.pagh.keebin;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import entity.CoffeeShop;
import userReST.AddCoffee;

/**
 * Created by kaspe on 2016-11-27.
 */

public class CoffeeShopArrayAdapter extends ArrayAdapter<CoffeeShop>
{
    private Context context;
    private List<CoffeeShop> shops;

    public CoffeeShopArrayAdapter(Context context, List<CoffeeShop> shops)
    {
        super(context, R.layout.coffee_shop_row, shops);
        this.shops = shops;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View viewRow = inflater.inflate(R.layout.coffee_shop_row, parent, false);

        TextView coffeeShopName = (TextView) viewRow.findViewById(R.id.shopName);
        TextView coffeeShopAdress = (TextView) viewRow.findViewById(R.id.address);
        Button button = (Button) viewRow.findViewById(R.id.mapsButton);

        final int pos = position;
        button.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {

                                          ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment, Map.newInstance(shops.get(pos))).commit();
                                      }
                                  });

        coffeeShopName.setText(shops.get(position).getActualBrandName());
        coffeeShopAdress.setText(shops.get(position).getAddress());


        return viewRow;
    }
}
