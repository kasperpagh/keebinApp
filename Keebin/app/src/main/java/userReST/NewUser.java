package userReST;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import entity.User;
import kasper.pagh.keebin.AsyncResponse;

/**
 * Created by kaspe on 2016-10-29.
 */

public class NewUser extends AsyncTask<String, Void, String>
{
    public AsyncResponse delegate = null;
    private String baseUrl = "http://82.211.198.31:3000/api/";
    private String userToCreate = null;
    private Gson gson;

    public NewUser(String baseUrl, User newUser, AsyncResponse delegate)
    {
        this.baseUrl = baseUrl;
        this.gson = new Gson();
        this.userToCreate = gson.toJson(newUser, User.class);
        this.delegate = delegate;

    }
    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            return newUser();
        } catch (IOException e)
        {
            return "500";
        }
    }
    @Override
    protected void onPostExecute(String result)
    {
        delegate.processFinished(result);
    }


    private String newUser() throws IOException
    {
        OutputStream output = null;

        URL url = new URL(baseUrl + "users/user/new");
        Log.d("full url: ", url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        output = connection.getOutputStream();
        output.write(userToCreate.getBytes("UTF-8"));
        output.close();

        connection.connect();
        String responseCode = connection.getResponseCode()+"";
        return responseCode;
    }
}
