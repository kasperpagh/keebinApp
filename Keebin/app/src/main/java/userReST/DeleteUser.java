package userReST;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import kasper.pagh.keebin.AsyncResponse;

/**
 * Created by kaspe on 2016-10-29.
 */

public class DeleteUser extends AsyncTask<String, Void, String>
{
    private String userEmail;
    public AsyncResponse delegate = null;
    private String baseUrl;

    public DeleteUser(String baseUrl, String userEmail, AsyncResponse delegate)
    {
        this.baseUrl = baseUrl;
        this.userEmail = userEmail;
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
        URL url = new URL(baseUrl + "users/user/" + userEmail);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod("DELETE");
        connection.connect();
        String code = "" +connection.getResponseCode();
        return code;
    }
}
