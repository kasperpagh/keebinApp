package restPack;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import kasper.pagh.keebin.AsyncResponse;
/**
 * Created by noncowi on 30-10-2016.
 */

public class DeleteOrderRest extends AsyncTask<String, Void, String>
{
    private String orderId;
    public AsyncResponse delegate = null;
    private String baseUrl;

    public DeleteOrderRest(String baseUrl, String orderId, AsyncResponse delegate)
    {
        this.baseUrl = baseUrl;
        this.orderId = orderId;
        this.delegate = delegate;
    }
    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            return deleteUser();
        } catch (IOException e)
        {
            e.printStackTrace();
            return "500";

        }

    }
    @Override
    protected void onPostExecute(String result)
    {
        delegate.processFinished(result);
    }

    private String deleteUser() throws IOException
    {
        URL url = new URL(baseUrl + "order/order/" + orderId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod("DELETE");
        connection.connect();
        String code = "" +connection.getResponseCode();
        return code;
    }
}
