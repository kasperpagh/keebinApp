package restPack;

/**
 * Created by noncowi on 30-10-2016.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import kasper.pagh.keebin.AsyncResponse;

public class GetOrderByID extends AsyncTask<String, Void, String> {

    private String orderID;
    public AsyncResponse delegate = null;
    private String baseUrl;

    public GetOrderByID(String baseUrl, String orderID, AsyncResponse delegate) {
        this.baseUrl = baseUrl;
        this.orderID = orderID;
        this.delegate = delegate;
    }


    private String getUser() throws IOException {
        InputStream input = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = null;

        try {
            URL url = new URL(baseUrl + "order/order" + orderID);
            Log.d("full url: ", url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            connection.setRequestProperty("Accept", "application/json");

            connection.connect();

            input = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(input));
            sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }
            bufferedReader.close();
            return sb.toString();
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return getUser();
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinished(result);
    }
}
