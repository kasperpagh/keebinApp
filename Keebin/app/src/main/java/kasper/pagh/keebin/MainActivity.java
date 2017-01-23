package kasper.pagh.keebin;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

import DB.GetCoffeeBrandsAndSaveToDB;
import entity.User;
import rest.userReST.DeleteUser;
import rest.userReST.NetworkChecker;
import rest.userReST.NewUser;
import rest.userReST.PutUser;


//All activities that deal with network MUST implement the AsyncResponse interface to get the data from the request. See: http://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
public class MainActivity extends AppCompatActivity implements AsyncResponse
{

    private static NetworkChecker networkChecker;

    private int MY_PERMISSION_REQUEST = 123;
    private PutUser putUser;
    private DeleteUser deleteUser;
    private NewUser newUser;
    private Gson gson = new Gson();
    public static User currentUser;
    PopupMenu MenuPopup;
    FloatingActionButton fab;
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
        String[] permissions = new String[2];
        permissions[0]=Manifest.permission.ACCESS_FINE_LOCATION;
        permissions[1]=Manifest.permission.ACCESS_COARSE_LOCATION;
        ActivityCompat.requestPermissions(this,permissions,
                MY_PERMISSION_REQUEST);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment, AddCoffeeToLoyaltycardFragment.newInstance()).commit();
            }
        });

        networkChecker = new NetworkChecker(this);
        Intent intent = getIntent();
        String unparsedCurrentUser = intent.getStringExtra("unparsedCurrentUser");
        currentUser = gson.fromJson(unparsedCurrentUser, User.class);

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, IndexFragment.newInstance()).commit();
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

        ImageButton button = (ImageButton) findViewById(R.id.home_menu);
        button.setEnabled(false);
        getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment, IndexFragment.newInstance()).commit();
        button.setEnabled(true);
        fab.setVisibility(View.VISIBLE);
    }

    public void link_maps(View v)
    {
        ImageButton button = (ImageButton) findViewById(R.id.maps_menu);
        button.setEnabled(false);
        getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment, MapFragment.newInstance()).commit();
        button.setEnabled(true);
        fab.setVisibility(View.GONE);
    }

    public void link_search(View v)
    {
        ImageButton button = (ImageButton) findViewById(R.id.search_menu);
        button.setEnabled(false);
        getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment, SearchCoffeeShopsFragment.newInstance()).commit();
        button.setEnabled(true);
        fab.setVisibility(View.VISIBLE);
    }

    public void link_loyaltycards(View v)
    {
        ImageButton button = (ImageButton) findViewById(R.id.loyaltycards_menu);
        button.setEnabled(false);
        getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment, UsersLoyaltyCardsFragment.newInstance()).commit();
        button.setEnabled(true);
        fab.setVisibility(View.VISIBLE);
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
