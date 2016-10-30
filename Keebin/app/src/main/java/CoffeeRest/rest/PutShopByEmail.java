package CoffeeRest.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import kasper.pagh.keebin.AsyncResponse;

/**
 * Created by kaspe on 2016-10-29.
 */

public class PutShopByEmail extends AsyncTask<String, Void, String>
{

    private String coffeeShopEmail;
    private String email;
    private String address;
    private int phone;
    private int brandID;
    public AsyncResponse delegate = null;
    private String baseUrl;
    private Gson gson;

    public PutShopByEmail(String baseUrl, String coffeeShopEmail, String email,
                          int brandID, String address, int phone, AsyncResponse delegate)
    {
        this.coffeeShopEmail = coffeeShopEmail;
        this.email = email;
        this.brandID = brandID;
        this.address = address;
        this.phone = phone;
        this.delegate = delegate;
        this.baseUrl = baseUrl;
        gson = new Gson();
    }


    private String putShop() throws IOException
    {
        JsonObject jo = new JsonObject();
        jo.addProperty("email", email);
        jo.addProperty("brandID", brandID);
        jo.addProperty("address", address);
        jo.addProperty("phone", phone);

        InputStream input = null;
        OutputStream output = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = null;

        try
        {
            URL url = new URL(baseUrl + "coffee/shop/" + coffeeShopEmail);
            Log.d("full url: ", url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            output = connection.getOutputStream();
            output.write(gson.toJson(jo).getBytes("UTF-8"));
            output.close();

            connection.connect();

            input = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(input));
            sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            bufferedReader.close();
            return sb.toString();
        } finally
        {
            if (input != null)
            {
                input.close();
            }
        }
    }

    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            return putShop();
        } catch (IOException e)
        {

            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result)
    {
        delegate.processFinished(result);
    }

}
