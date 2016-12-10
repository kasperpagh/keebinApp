package CoffeeRest.rest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import kasper.pagh.keebin.AsyncResponse;
import kasper.pagh.keebin.DatabaseHandler;

/**
 * Created by kaspe on 2016-10-29.
 */

public class NewBrand extends AsyncTask<String, Void, String>
{
    public AsyncResponse delegate = null;
    private String baseUrl;
    private Gson gson;
    private String brandName;
    private Integer numberOfCoffeeNeeded;
    private DatabaseHandler dbh;

    public NewBrand(String baseUrl, String brandName, Integer numberOfCoffeeNeeded, AsyncResponse delegate, Context context)
    {
        this.baseUrl = baseUrl;
        this.gson = new Gson();
        this.delegate = delegate;
        this.brandName = brandName;
        this.numberOfCoffeeNeeded = numberOfCoffeeNeeded;
        dbh = new DatabaseHandler(context);

    }
    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            return newBrand();
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


    private String newBrand() throws IOException
    {
        JsonObject jo = new JsonObject();
        jo.addProperty("brandName", brandName);
        jo.addProperty("numberOfCoffeeNeeded", numberOfCoffeeNeeded);
        OutputStream output = null;

        URL url = new URL(baseUrl + "coffee/brand/new");
        Log.d("full url: ", url.toString());
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

        String code = "" +connection.getResponseCode();
        if(code.equalsIgnoreCase("200"));
        {
            String accessToken = connection.getHeaderField("accessToken");
            Log.d("her er res aToken " , accessToken);
            if(!dbh.getTokenByName("accessToken").getTokenData().equals(accessToken))
            {
                dbh.updateToken("accessToken", accessToken);
                Log.d("accessToken", " er opdateret til " + accessToken);
            }

        }
        return code;
    }
}