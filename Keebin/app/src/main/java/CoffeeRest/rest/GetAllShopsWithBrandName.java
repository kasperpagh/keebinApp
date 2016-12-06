package CoffeeRest.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import entity.CoffeeBrand;
import entity.CoffeeShop;
import kasper.pagh.keebin.AsyncResponse;
import kasper.pagh.keebin.MainActivity;

/**
 * Created by kaspe on 2016-12-05.
 */

public class GetAllShopsWithBrandName extends AsyncTask<String, Void, String>
{

    public AsyncResponse delegate = null;
    private String baseUrl;
    private Gson gson = new Gson();

    public GetAllShopsWithBrandName(String baseUrl, AsyncResponse delegate)
    {
        this.baseUrl = baseUrl;
        this.delegate = delegate;
    }

    private String getBrands() throws IOException
    {
        InputStream input = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = null;

        try
        {
            URL url = new URL(baseUrl + "coffee/allbrands/");
            Log.d("full url: ", url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("accessToken", MainActivity.currentUser.getLoginData().getAccessToken());
            connection.setRequestProperty("refreshToken", MainActivity.currentUser.getLoginData().getRefreshToken());

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

    private String getShops() throws IOException
    {
        InputStream input = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = null;

        try
        {
            URL url = new URL(baseUrl + "coffee/allshops/");
            Log.d("full url: ", url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("accessToken", MainActivity.currentUser.getLoginData().getAccessToken());
            connection.setRequestProperty("refreshToken", MainActivity.currentUser.getLoginData().getRefreshToken());

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
            String shops = getShops();

            String brands = getBrands();

            CoffeeBrand[] brandList = gson.fromJson(brands, CoffeeBrand[].class);
            CoffeeShop[] coffeList = gson.fromJson(shops, CoffeeShop[].class);

            for(int i = 0; i < coffeList.length; i++)
            {
                for(int a = 0; a < brandList.length; a++)
                {
                    if(coffeList[i].getBrandName() == brandList[a].getId())
                    {
                        coffeList[i].setActualBrandName(brandList[a].getBrandName());
                    }
                }

            }

            return gson.toJson(coffeList);
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
