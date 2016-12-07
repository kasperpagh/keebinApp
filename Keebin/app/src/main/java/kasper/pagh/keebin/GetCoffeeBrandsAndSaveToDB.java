package kasper.pagh.keebin;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import CoffeeRest.rest.GetAllBrands;
import entity.CoffeeBrand;

/**
 * Created by mrlef on 12/4/2016.
 */

public class GetCoffeeBrandsAndSaveToDB implements AsyncResponse {

    private Gson gson = new Gson();
    DatabaseHandler dbh;
    Context context;

    public GetCoffeeBrandsAndSaveToDB(Context context) {
        this.context = context;
        dbh = new DatabaseHandler(context);
    }


    public void getAllCoffeeBrands(Context context) {
        GetAllBrands g = new GetAllBrands(context.getResources().getString(R.string.baseUrl), this);
        g.execute();
    }

    @Override
    public void processFinished(String output) {
        Log.d("this is output: ", "her kommer output fra GetCBandSaveToDB: " + output);





        CoffeeBrand[] cBrand = gson.fromJson(output, CoffeeBrand[].class);
        Log.d("+++--- ", " " + cBrand[0].getId());

        for (CoffeeBrand eachBrand : cBrand) {
            CoffeeBrand cBrandForDB = new CoffeeBrand(eachBrand.getId(), eachBrand.getBrandName(), eachBrand.getNumberOfCoffeeNeeded());
            Log.d("her er cBrandForDB", " : " + cBrandForDB.getBrandName() + " and the number: " + cBrandForDB.getNumberOfCoffeeNeeded()
                    + " and the id: " + cBrandForDB.getId());
            dbh.addCoffeeBrand(cBrandForDB);

        }

        List<CoffeeBrand> brands = dbh.getAllCoffeeBrands();
        for (CoffeeBrand cb : brands) {
            String log = "Id: " + cb.getId() + " ,Name: " + cb.getBrandName() + " ,coffeesNeeded: " + cb.getNumberOfCoffeeNeeded() + " ,dataBaseId: " + cb.getDataBaseId();
            // Writing CoffeeBrands to log
            Log.d("Name: ", log);
        }

    }
}
