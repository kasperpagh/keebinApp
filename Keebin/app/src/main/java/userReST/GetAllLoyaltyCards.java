package userReST;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import kasper.pagh.keebin.AsyncResponse;

/**
 * Created by kaspe on 2016-10-30.
 */

public class GetAllLoyaltyCards extends AsyncTask<String, Void, String>
{
    public AsyncResponse delegate = null;
    private String baseUrl;

    public GetAllLoyaltyCards(String baseUrl, AsyncResponse delegate)
    {
        this.baseUrl = baseUrl;
        this.delegate = delegate;
    }


    private String getUser() throws IOException
    {
        InputStream input = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = null;

        try
        {
            URL url = new URL(baseUrl + "users/allcards");
            Log.d("full url: ", url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("refreshToken", "2cc3cab0f-9d48-41b1-a0a3-028ee83fed81");
            connection.setRequestProperty("accessToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7InN1YiI6MiwiaXNzIjoid3d3LmtlZWJpbi5kayIsImVtYWlsIjoiYkBnbWFpbC5jb20iLCJyb2xlSWQiOjF9LCJpYXQiOjE0ODA2OTk2NzIsImV4cCI6MTQ4MzI5MTY3Mn0.A8tvRUzG_4Gj12pzZ4UT8v-N2qHuikOhDgE9Wv8xx8U");

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
            return getUser();
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
