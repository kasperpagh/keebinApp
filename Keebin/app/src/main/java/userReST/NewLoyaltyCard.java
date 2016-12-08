package userReST;

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

public class NewLoyaltyCard extends AsyncTask<String, Void, String>
{
    public AsyncResponse delegate = null;
    private java.lang.String baseUrl;
    private Gson gson;
    private int userId;
    private int numberOfCoffeesBought;
    private int brandName;
    private DatabaseHandler dbh;

    //ville anbefale vi lavede backend om til at virke på email og ikke id
    public NewLoyaltyCard(String baseUrl, int brandId ,int userId, int numberOfCoffeesBought, AsyncResponse delegate, Context context)
    {
        this.baseUrl = baseUrl;
        this.gson = new Gson();
        this.numberOfCoffeesBought = numberOfCoffeesBought;
        this.userId = userId;
        this.delegate = delegate;
        this.brandName = brandId;
        dbh = new DatabaseHandler(context);
    };

    @Override
    protected java.lang.String doInBackground(java.lang.String... params)
    {
        try
        {
            return newLoyaltyCard();
        } catch (IOException e)
        {
            return "500";
        }
    }

    @Override
    protected void onPostExecute(java.lang.String result)
    {
        delegate.processFinished(result);
    }


    private String newLoyaltyCard() throws IOException
    {
        JsonObject jo = new JsonObject();
        jo.addProperty("userId", userId);
        jo.addProperty("numberOfCoffeesBought", numberOfCoffeesBought);
        jo.addProperty("brandName", brandName);
        OutputStream output = null;

        URL url = new URL(baseUrl + "users/card/new");
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
        java.lang.String responseCode = connection.getResponseCode() + "";

        if(responseCode.equalsIgnoreCase("200"));
        {
            String refreshToken = connection.getHeaderField("refreshToken");
            String accessToken = connection.getHeaderField("accessToken");

            if(!dbh.getTokenByName("refreshToken").getTokenData().equals(refreshToken))
            {
                dbh.updateToken("refreshToken", refreshToken);
            }
            if(!dbh.getTokenByName("accessToken").getTokenData().equals(accessToken))
            {
                dbh.updateToken("accessToken", accessToken);
            }

        }
        return responseCode;
    }
}