package CoffeeRest.rest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import kasper.pagh.keebin.AsyncResponse;
import kasper.pagh.keebin.DatabaseHandler;

/**
 * Created by kaspe on 2016-10-30.
 */

public class GetAllShopUsersByID extends AsyncTask<String, Void, String>
{
    public AsyncResponse delegate = null;
    private Integer shopID;
    private String baseUrl;
    private DatabaseHandler dbh;

    public GetAllShopUsersByID(String baseUrl, Integer shopID, AsyncResponse delegate, Context context)
    {
        this.baseUrl = baseUrl;
        this.shopID = shopID;
        this.delegate = delegate;
        dbh = new DatabaseHandler(context);
    }


    private String getShopUsers() throws IOException
    {
        InputStream input = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = null;

        try
        {
            URL url = new URL(baseUrl + "coffee/allshopusers/" + shopID);
            Log.d("full url: ", url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            connection.setRequestProperty("Accept", "application/json");

            connection.connect();

            input = connection.getInputStream();
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
            return getShopUsers();
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
