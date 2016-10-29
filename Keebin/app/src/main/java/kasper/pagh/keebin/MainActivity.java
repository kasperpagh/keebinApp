package kasper.pagh.keebin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import entity.User;
import restPack.DeleteUser;
import restPack.NetworkChecker;
import restPack.NewUser;
import restPack.PutUser;


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


    }

    public void putUser(View view)
    {

        User postUsr = new User("nyBruger", "numer 1", "new@gmail.com", "2010-09-08 22:00:00", "male", 1, "asdf");
//        String jsonUsr = gson.toJson(putusr, User.class);
//        Log.d("her er json user: ", jsonUsr);
//        putUser = new PutUser("lars1@gmail.com");
//        putUser.delegate = this;
//        deleteUser = new DeleteUser(getResources().getString(R.string.baseUrl), "lars2@gmail.com");
//        deleteUser.delegate = this;
        newUser = new NewUser(getResources().getString(R.string.baseUrl), postUsr);
        newUser.delegate = this;


        if (networkChecker.networkChecker())
        {
//            putUser.execute(jsonUsr);
//            deleteUser.execute();
            newUser.execute();

        }

    }


    public static NetworkChecker getNetworkChecker()
    {
        return networkChecker;
    }

    @Override
    public void processFinished(String output)
    {
        Log.d("i porc fin", "john");
        TextView textView = (TextView) this.findViewById(R.id.responseText);
        textView.setText(output);
    }
}
