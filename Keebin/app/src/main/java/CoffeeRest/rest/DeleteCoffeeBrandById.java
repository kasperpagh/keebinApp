package CoffeeRest.rest;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import kasper.pagh.keebin.AsyncResponse;


public class DeleteCoffeeBrandById extends AsyncTask<String, Void, String>
{
    private String BrandID;
    public AsyncResponse delegate = null;
    private String baseUrl;

    public DeleteCoffeeBrandById(String baseUrl, String BrandID, AsyncResponse delegate)
    {
        this.baseUrl = baseUrl;
        this.BrandID = BrandID;
        this.delegate = delegate;
    }
    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            return deleteBrand();
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

    private String deleteBrand() throws IOException
    {
        URL url = new URL(baseUrl + "coffee/shop/" + BrandID);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod("DELETE");
        connection.connect();
        String code = "" +connection.getResponseCode();
        return code;
    }
}
