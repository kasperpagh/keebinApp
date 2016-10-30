package restPack;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import entity.OrderItem;
import kasper.pagh.keebin.AsyncResponse;

/**
 * Created by noncowi on 30-10-2016.
 */

public class NewOrderItem extends AsyncTask<String, Void, String>
{
    public AsyncResponse delegate = null;
    private String baseUrl;
    private String orderItemToCreate = null;
    private Gson gson;

    public NewOrderItem(String baseUrl, OrderItem newOrderItem, AsyncResponse delegate)
    {
        this.baseUrl = baseUrl;
        this.gson =new Gson();
        this.orderItemToCreate = gson.toJson(newOrderItem, OrderItem.class);
        this.delegate = delegate;

    }
    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            return newOrderItem();
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


    private String newOrderItem() throws IOException
    {
        OutputStream output = null;

        URL url = new URL(baseUrl + "order/orderItem/new");
        Log.d("full url: ", url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        output = connection.getOutputStream();
        output.write(orderItemToCreate.getBytes("UTF-8"));
        output.close();

        connection.connect();
        String responseCode = connection.getResponseCode()+"";
        return responseCode;
    }}
