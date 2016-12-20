package userReST

import android.content.Context
import android.os.AsyncTask
import android.util.Log

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

import kasper.pagh.keebin.AsyncResponse
import kasper.pagh.keebin.DatabaseHandler
import kasper.pagh.keebin.Manifest
import kasper.pagh.keebin.R
import java.util.*

/**
 * Created by kaspe on 2016-10-30.
 */




class GetAllLoyaltyCards( delegate: AsyncResponse, context: Context) : AsyncTask<String, Void, String>() {
    var delegate: AsyncResponse? = null
    private val dbh: DatabaseHandler
    private val url: URL


    init {
        this.delegate = delegate
        dbh = DatabaseHandler(context)

        url = URL(context.getResources().getString(R.string.baseUrl) + "users/allcards")
    }


    private val user: String
        @Throws(IOException::class)
        get() {

           return makeGetCall(url,dbh)
        }

    override fun doInBackground(vararg params: String): String? {
        try {
            return user
        } catch (e: IOException) {

            e.printStackTrace()
            return null
        }

    }

    override fun onPostExecute(result: String) {
        delegate!!.processFinished(result)
    }

}
//package userReST;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import kasper.pagh.keebin.AsyncResponse;
//import kasper.pagh.keebin.DatabaseHandler;
//
///**
// * Created by kaspe on 2016-10-30.
// */
//
//public class GetAllLoyaltyCards extends AsyncTask<String, Void, String>
//{
//    public AsyncResponse delegate = null;
//    private String baseUrl;
//    private DatabaseHandler dbh;
//
//    public GetAllLoyaltyCards(String baseUrl, AsyncResponse delegate, Context context)
//    {
//        this.baseUrl = baseUrl;
//        this.delegate = delegate;
//        dbh = new DatabaseHandler(context);
//    }
//
//
//    private String getUser() throws IOException
//    {
//        InputStream input = null;
//        BufferedReader bufferedReader = null;
//        StringBuilder sb = null;
//
//        try
//        {
//            URL url = new URL(baseUrl + "users/allcards");
//            Log.d("full url: ", url.toString());
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setReadTimeout(10000);
//            connection.setConnectTimeout(15000);
//            connection.setDoInput(true);
//            connection.setRequestProperty("Accept", "application/json");
//            connection.setRequestProperty("accessToken", dbh.getTokenByName("accessToken").getTokenData());
//            connection.setRequestProperty("refreshToken", dbh.getTokenByName("refreshToken").getTokenData());
//
//            connection.connect();
//
//
//            input = connection.getInputStream();
//            String code = "" +connection.getResponseCode();
//
//
//
//            if(code.equalsIgnoreCase("200"));
//            {
//                String accessToken = connection.getHeaderField("accessToken");
//
//
//                if(!dbh.getTokenByName("accessToken").getTokenData().equals(accessToken))
//                {
//                    dbh.updateToken("accessToken", accessToken);
//                }
//
//            }
//
//            bufferedReader = new BufferedReader(new InputStreamReader(input));
//            sb = new StringBuilder();
//            String line;
//
//            while ((line = bufferedReader.readLine()) != null)
//            {
//                sb.append(line + "\n");
//            }
//            bufferedReader.close();
//            return sb.toString();
//        } finally
//        {
//            if (input != null)
//            {
//                input.close();
//            }
//        }
//    }
//
//    @Override
//    protected String doInBackground(String... params)
//    {
//        try
//        {
//            return getUser();
//        } catch (IOException e)
//        {
//
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    protected void onPostExecute(String result)
//    {
//        delegate.processFinished(result);
//    }
//
//}