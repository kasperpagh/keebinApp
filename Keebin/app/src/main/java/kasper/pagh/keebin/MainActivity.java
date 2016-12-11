package kasper.pagh.keebin;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    public static User currentUser;
    //You need to declare this for each of the ReST classes you want to use in this activity


    public User getCurrentUser()
    {
        return currentUser;
    }


//    public void bob(View view)
//    {
//        Toast.makeText(this, "Du har klikket på den fabulous FAB!!",Toast.LENGTH_LONG).show();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

     ///////////////Dette er click lytteren for vores addCoffee floating button thingy   
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //Logik såsom nyt fragment og eventuelle restcalls skal være i denne metode (altså i stedet for toasten)!
                Toast.makeText(getApplicationContext(), "Du har klikket på den fabulous FAB!!",Toast.LENGTH_LONG).show();
            }
        });
//////////////////////

        networkChecker = new NetworkChecker(this);
        Intent intent = getIntent();
        String unparsedCurrentUser = intent.getStringExtra("unparsedCurrentUser");
        currentUser = gson.fromJson(unparsedCurrentUser, User.class);

        if (savedInstanceState == null)
        {

            getSupportFragmentManager().beginTransaction().add(R.id.fragment, Index.newInstance()).commit();

        }
        GetCoffeeBrandsAndSaveToDB yes = new GetCoffeeBrandsAndSaveToDB(getApplicationContext());
        yes.getAllCoffeeBrands(getApplicationContext());

    }


    public static NetworkChecker getNetworkChecker()
    {
        return networkChecker;
    }

    public void saveUser(View view)
    {


        PutUser putuser = new PutUser(MainActivity.currentUser, this, getApplication());

        putuser.execute();

    }

    @Override
    public void processFinished(String output)
    {
        Gson gson = new Gson();
        User user = gson.fromJson(output, User.class);
        MainActivity.currentUser = user;
    }


    public void link_home(View view)
    {
        getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment, Index.newInstance()).commit();
    }

    public void link_search(View view)
    {
        getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment, SearchCoffeeShopsFragment.newInstance()).commit();
    }

    PopupMenu settingsPopup;

    public void SettingsPopup(View v)
    {
        if (settingsPopup == null)
        {
            settingsPopup = new PopupMenu(this, v);
            MenuInflater inflater = settingsPopup.getMenuInflater();
            inflater.inflate(R.menu.menu, settingsPopup.getMenu());
        }

        settingsPopup.show();
    }


    PopupMenu MenuPopup;

    public void MenuPopup(View v)
    {

        Log.d("I am run ", " 2");
        if (MenuPopup == null)
        {
            MenuPopup = new PopupMenu(this, v);
            MenuInflater inflater = MenuPopup.getMenuInflater();
            inflater.inflate(R.menu.menu, MenuPopup.getMenu());
        }
        MenuPopup.show();


    }


}
