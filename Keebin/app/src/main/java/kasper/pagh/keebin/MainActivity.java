package kasper.pagh.keebin;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import restPack.NetworkChecker;


//All activities that deal with network MUST implement the AsyncResponse interface to get the data from the request. See: http://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
public class MainActivity extends AppCompatActivity implements AsyncResponse
{

    private static NetworkChecker networkChecker;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public static NetworkChecker getNetworkChecker()
    {
        return networkChecker;
    }

    @Override
    public void processFinished(String output)
    {

    }
}
