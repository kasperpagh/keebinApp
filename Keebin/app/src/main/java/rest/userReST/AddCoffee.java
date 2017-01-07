package rest.userReST;
import android.content.Context;
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
import DB.DatabaseHandler;

/**
 * Created by kaspe on 2016-10-29.
 */

public class AddCoffee extends AsyncTask<String, Void, String>
{
    public AsyncResponse delegate = null;
    private String baseUrl;
    private Integer id = null;
    private String coffeeCode;
    private String numberOfCoffeesBought;
    private Gson gson;
    private DatabaseHandler dbh;

    public AddCoffee(String baseUrl, User user,String coffeeCode,String numberOfCoffeesBought, AsyncResponse delegate, Context context)
    {
        this.baseUrl = baseUrl;
        this.gson = new Gson();
        this.id = user.getId();
        this.coffeeCode = coffeeCode;
        this.numberOfCoffeesBought = numberOfCoffeesBought;
        this.delegate = delegate;
        dbh = new DatabaseHandler(context);
    }
    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            return addCoffeeToLoyal();
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


    private String addCoffeeToLoyal() throws IOException
    {
        JsonObject jo = new JsonObject();
        jo.addProperty("userId", this.id);
        jo.addProperty("numberOfCoffeesBought", this.numberOfCoffeesBought);
        jo.addProperty("coffeeCode", this.coffeeCode);
        OutputStream output = null;

        URL url = new URL(baseUrl + "users/card/coffeeBought");
        Log.d("full url: ", url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("accessToken", dbh.getTokenByName("accessToken").getTokenData());
        connection.setRequestProperty("refreshToken", dbh.getTokenByName("refreshToken").getTokenData());
        output = connection.getOutputStream();
        output.write(gson.toJson(jo).getBytes("UTF-8"));
        output.close();

        connection.connect();
        String responseCode = connection.getResponseCode()+"";

        if(responseCode.equalsIgnoreCase("200"));
        {
            String accessToken = connection.getHeaderField("accessToken");

            if(!dbh.getTokenByName("accessToken").getTokenData().equals(accessToken))
            {
                dbh.updateToken("accessToken", accessToken);
            }

        }
        return responseCode;
    }
}