package CoffeeRest.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import entity.User;
import kasper.pagh.keebin.AsyncResponse;

/**
 * Created by kaspe on 2016-10-29.
 */

public class NewShopUser extends AsyncTask<String, Void, String>
{
    public AsyncResponse delegate = null;
    private String baseUrl;
    private String userEmail;
    private String coffeeShopEmail;
    private Gson gson;

    public NewShopUser(String baseUrl, String userEmail, String coffeeShopEmail, AsyncResponse delegate)
    {
        this.baseUrl = baseUrl;
        this.gson = new Gson();
        this.userEmail = userEmail;
        this.coffeeShopEmail = coffeeShopEmail;
        this.delegate = delegate;

    }
    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            return newShopUser();
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


    private String newShopUser() throws IOException
    {
        JsonObject jo = new JsonObject();
        jo.addProperty("userEmail", userEmail);
        jo.addProperty("coffeeShopEmail", coffeeShopEmail);
        OutputStream output = null;

        URL url = new URL(baseUrl + "coffee/shopuser/new");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        output = connection.getOutputStream();
        output.write(gson.toJson(jo).getBytes("UTF-8"));
        output.close();

        connection.connect();
        String responseCode = connection.getResponseCode() + "";
        return responseCode;
    }
}
