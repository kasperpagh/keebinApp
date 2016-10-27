package kasper.pagh.keebin;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Date;

import entity.User;
import restPack.NetworkChecker;
import restPack.PutUser;
import restPack.UserRest;


//All activities that deal with network MUST implement the AsyncResponse interface to get the data from the request. See: http://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
public class MainActivity extends AppCompatActivity implements AsyncResponse
{

    private static NetworkChecker networkChecker;
    private PutUser putUser;
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
        User putusr = new User(1, "llama", "john","lars1@gmail.com", "2010-09-08 22:00:00" , "male", 1,false,"asdf");
        String jsonUsr = gson.toJson(putusr, User.class);
        Log.d("her er json user: ", jsonUsr);
        putUser = new PutUser("lars1@gmail.com");
        putUser.delegate = this;
        if(networkChecker.networkChecker())
        {
            putUser.execute(jsonUsr);
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
