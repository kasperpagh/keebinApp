package rest.userReST;

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
import DB.DatabaseHandler;

/**
 * Created by kaspe on 2016-10-29.
 */

public class GetUser extends AsyncTask<String, Void, String>
{

    private String userEmail;
    public AsyncResponse delegate = null;
    private String baseUrl;
    private DatabaseHandler dbh;

    public GetUser(String baseUrl, String userEmail, AsyncResponse delegate, Context context)
    {
        this.baseUrl = baseUrl;
        this.userEmail = userEmail;
        this.delegate = delegate;
        dbh = new DatabaseHandler(context);
    }


    private String getUser() throws IOException
    {
        InputStream input = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = null;

        try
        {
            URL url = new URL(baseUrl + "users/user/" + userEmail);
            Log.d("full url: ", url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("accessToken", dbh.getTokenByName("accessToken").getTokenData());
            connection.setRequestProperty("refreshToken", dbh.getTokenByName("refreshToken").getTokenData());
            Log.d("præ rToken ", dbh.getTokenByName("refreshToken").getTokenData());
            Log.d("præ aToken ", dbh.getTokenByName("accessToken").getTokenData());

            connection.connect();

            input = connection.getInputStream();

            String code = "" +connection.getResponseCode();

            if(code.equalsIgnoreCase("200"));
            {
                String accessToken = connection.getHeaderField("accessToken");


                if(!dbh.getTokenByName("accessToken").getTokenData().equals(accessToken))
                {
                    dbh.updateToken("accessToken", accessToken);
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
