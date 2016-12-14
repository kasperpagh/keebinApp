package kasper.pagh.keebin;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.google.gson.Gson;

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
    PopupMenu MenuPopup;
    //You need to declare this for each of the ReST classes you want to use in this activity


    public User getCurrentUser()
    {
        return currentUser;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment, AddCoffeeToLoyaltycard.newInstance()).commit();
            }
        });

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

    public void link_home(View v)
    {
        getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment, Index.newInstance()).commit();
    }

    public void link_maps(View v)
    {
        getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment, Map.newInstance()).commit();
    }

    public void link_search(View v)
    {
        getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment, SearchCoffeeShopsFragment.newInstance()).commit();
    }

    public void link_loyaltycards(View v)
    {
        getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment, UsersLoyaltyCards.newInstance()).commit();
    }

    PopupMenu settingsPopup;

    public void SettingsPopup(View v)
    {
        if (settingsPopup == null)
        {
            settingsPopup = new PopupMenu(this, v);
            MenuInflater inflater = settingsPopup.getMenuInflater();
            inflater.inflate(R.menu.settings, settingsPopup.getMenu());
        }
        settingsPopup.show();
    }


    public void MenuPopup(View v)
    {
        if (MenuPopup == null)
        {
            MenuPopup = new PopupMenu(this, v);
            MenuInflater inflater = MenuPopup.getMenuInflater();
            inflater.inflate(R.menu.menu, MenuPopup.getMenu());
        }
        MenuPopup.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection

        return super.onOptionsItemSelected(item);
    }

}
