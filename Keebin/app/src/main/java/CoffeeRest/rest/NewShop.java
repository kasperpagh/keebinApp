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

public class NewShop extends AsyncTask<String, Void, String>
{
    public AsyncResponse delegate = null;
    private String baseUrl;
    private Gson gson;
    private String email;
    private String address;
    private int phone;
    private int brandID;
    private DatabaseHandler dbh;

    //ville anbefale vi lavede backend om til at virke på email og ikke id
    public NewShop(String baseUrl, String email , int brandID, String address, int phone,  AsyncResponse delegate, Context context)
    {
        this.baseUrl = baseUrl;
        this.gson = new Gson();
        this.email = email;
        this.address = address;
        this.delegate = delegate;
        this.brandID = brandID;
        this.phone = phone;
        dbh = new DatabaseHandler(context);
    }

    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            return newShop();
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


    private String newShop() throws IOException
    {
        JsonObject jo = new JsonObject();
        jo.addProperty("email", email);
        jo.addProperty("address", address);
        jo.addProperty("phone", phone);
        jo.addProperty("brandName", brandID);
        OutputStream output = null;

        URL url = new URL(baseUrl + "coffee/shop/new");
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