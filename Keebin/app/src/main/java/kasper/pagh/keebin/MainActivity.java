package kasper.pagh.keebin;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import restPack.NetworkChecker;

public class MainActivity extends AppCompatActivity
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
}
