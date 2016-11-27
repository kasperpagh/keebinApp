package kasper.pagh.keebin;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import CoffeeRest.rest.PutBrandByID;
import CoffeeRest.rest.PutShopByEmail;
import entity.User;
import userReST.DeleteUser;
import userReST.NetworkChecker;
import userReST.NewUser;
import userReST.PutUser;


//All activities that deal with network MUST implement the AsyncResponse interface to get the data from the request. See: http://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
public class MainActivity extends AppCompatActivity implements AsyncResponse
{

    private static NetworkChecker networkChecker;

    private PutUser putUser;
    private DeleteUser deleteUser;
    private NewUser newUser;
    private Gson gson = new Gson();
    //You need to declare this for each of the ReST classes you want to use in this activity


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkChecker = new NetworkChecker(this);
        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, SearchCoffeeShopsFragment.newInstance()).commit();
        }
    }

    public void tester(View view)
    {

// KASPERS 
        User postUsr = new User("nyBruger", "numer 1", "new@gmail.com", "2010-09-08 22:00:00", "male", 1, "asdf");
//        String jsonUsr = gson.toJson(putusr, User.class);
//        Log.d("her er json user: ", jsonUsr);
//        tester = new PutUser("lars1@gmail.com");
//        tester.delegate = this;
//        deleteUser = new DeleteUser(getResources().getString(R.string.baseUrl), "lars2@gmail.com");
//        deleteUser.delegate = this;
//        newUser = new NewUser(getResources().getString(R.string.baseUrl), postUsr);
//        newUser.delegate = this;

//        NewLoyaltyCard nlc = new NewLoyaltyCard(getResources().getString(R.string.baseUrl),1, 4, 3, this);
//        NewRole newRole = new NewRole(getResources().getString(R.string.baseUrl), "jimmy", this);
//        GetUser gu = new GetUser("new@gmail.com", this);
//        GetLoyaltyCardById glcbid = new GetLoyaltyCardById("5", this);
//        PutLoyaltyCard pLC = new PutLoyaltyCard(getResources().getString(R.string.baseUrl),4, 5,1,6,this);
//        GetAllRoles gAR = new GetAllRoles(getResources().getString(R.string.baseUrl), this);
//        GetAllLoyaltyCards gALC = new GetAllLoyaltyCards(getResources().getString(R.string.baseUrl), this);
//        GetAllUsers gAU = new GetAllUsers(getResources().getString(R.string.baseUrl), this);

// SEBASTIANS
//        DeleteCoffeeShopByEmail dCSBE = new DeleteCoffeeShopByEmail(getResources().getString(R.string.baseUrl), "a@ho.hom", this); // tested -- working
//        NewBrand nB = new NewBrand(getResources().getString(R.string.baseUrl), "MyBrandName", 10, this); // tested -- working
//        NewShop nS = new NewShop(getResources().getString(R.string.baseUrl), "myemail@hotmail.com", 3, "myadress 12", 12345678, this); // tested -- working
//        NewShopUser nSU = new NewShopUser(getResources().getString(R.string.baseUrl), "john@gmailae.com", "myemail@hotmail.com", this); // tested -- working
//        GetBrandByID gBBI = new GetBrandByID(getResources().getString(R.string.baseUrl), 2, this); // tested -- working
//        GetShopByEmail gSBE = new GetShopByEmail(getResources().getString(R.string.baseUrl), "myemail@hotmail.com", this); // tested -- working
//        GetAllShops gAS = new GetAllShops(getResources().getString(R.string.baseUrl), this); // tested -- working
//        GetAllBrands gAB = new GetAllBrands(getResources().getString(R.string.baseUrl), this); // tested -- working
//        GetAllShopUsersByID gASUBI = new GetAllShopUsersByID(getResources().getString(R.string.baseUrl), 7, this); // tested -- working
//        PutBrandByID pBBI = new PutBrandByID(getResources().getString(R.string.baseUrl), "Hello lars",2,10, this); // tested -- working
//        PutShopByEmail pSBE = new PutShopByEmail(getResources().getString(R.string.baseUrl), "a@ho.hon", "newshopemail@hotmail.com", 3, "shop address", 12345678, this); // tested -- working

        if (networkChecker.networkChecker())
        {
//            tester.execute(jsonUsr);
//            deleteUser.execute();
//            newUser.execute();
//            nlc.execute();
//            newRole.execute();
//            gu.execute();
//            glcbid.execute();
//            pLC.execute();
//            gAR.execute();
//            gALC.execute();
//            gAU.execute();
//            nB.execute();
//            nS.execute();
//            nSU.execute();
//            gBBI.execute();
//            gSBE.execute();
//            gAS.execute();
//            gAB.execute();
//            gASUBI.execute();
//            pBBI.execute();
//            pSBE.execute();



        }

    }


    public static NetworkChecker getNetworkChecker()
    {
        return networkChecker;
    }

    @Override
    public void processFinished(String output)
    {
//        Log.d("i porc fin", "john");
//        TextView textView = (TextView) this.findViewById(R.id.resText);
//        textView.setText(output);
    }
}
