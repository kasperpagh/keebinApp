package kasper.pagh.keebin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import CoffeeRest.rest.GetAllBrands;
import entity.CoffeeBrand;
import entity.LoyaltyCard;

/**
 * Created by mrlef on 12/4/2016.
 */

public class GetCoffeeBrandsAndSaveToDB extends Activity implements AsyncResponse {

    private Gson gson = new Gson();
    DatabaseHandler dbh = new DatabaseHandler(this);
    Context context;

    public GetCoffeeBrandsAndSaveToDB(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    public void getAllCoffeeBrands(){
        GetAllBrands g = new GetAllBrands("http://192.168.2.105:3000/api/", this);
    g.execute();
    }

    @Override
    public void processFinished(String output) {
        Log.d("this is output: ", "her kommer output fra GetCBandSaveToDB: " + output);



        CoffeeBrand[] cBrand = gson.fromJson(output, CoffeeBrand[].class);

        for (CoffeeBrand eachBrand : cBrand) {
            CoffeeBrand cBrandForDB = new CoffeeBrand(eachBrand.getDatabaseId(), eachBrand.getBrandName(), eachBrand.getCoffeesNeeded());
            Log.d("her er cBrandForDB", " : " + cBrandForDB.getBrandName() + " and the number: " + cBrandForDB.getCoffeesNeeded());
            dbh.addCoffeeBrand(cBrandForDB);

        }

        List<CoffeeBrand> brands = dbh.getAllCoffeeBrands();

        for (CoffeeBrand cb : brands) {
            String log = "Id: "+cb.getDatabaseId()+" ,Name: " + cb.getBrandName() + " ,coffeesNeeded: " + cb.getCoffeesNeeded();
            // Writing CoffeeBrands to log
            Log.d("Name: ", log);
        }

    }
}
