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

public class PutBrandByID extends AsyncTask<String, Void, String>
{

    private String brandName;
    private int brandID;
    private int numberOfCoffeeNeeded;
    public AsyncResponse delegate = null;
    private String baseUrl;
    private Gson gson;

    public PutBrandByID(String baseUrl, String brandName, int brandID, int numberOfCoffeeNeeded, AsyncResponse delegate)
    {
        this.brandName = brandName;
        this.brandID = brandID;
        this.numberOfCoffeeNeeded = numberOfCoffeeNeeded;
        this.delegate = delegate;
        this.baseUrl = baseUrl;
        gson = new Gson();
    }


    private String putBrand() throws IOException
    {
        JsonObject jo = new JsonObject();
        jo.addProperty("numberOfCoffeeNeeded", numberOfCoffeeNeeded);
        jo.addProperty("brandName", brandName);


        InputStream input = null;
        OutputStream output = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = null;

        try
        {
            URL url = new URL(baseUrl + "coffee/brand/" + brandID);
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
            return putBrand();
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