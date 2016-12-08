package userReST;

import android.content.Context;
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
import java.util.List;

import entity.CoffeeBrand;
import entity.LoginData;
import entity.Token;
import entity.User;
import kasper.pagh.keebin.AsyncResponse;
import kasper.pagh.keebin.DatabaseHandler;

/**
 * Created by kaspe on 2016-11-30.
 */

public class PostLogin extends AsyncTask<String, Void, String>
{
    private User currentUser;
    public AsyncResponse delegate = null;
    private LoginData loginData;
    private String rawLoginData;
    private String baseUrl;
    private Gson gson;
    private String userEmail;
    private String userPassword;
    private String rawUser;
    private DatabaseHandler dbh;
    private Context context;

    public PostLogin(String baseUrl, String userEmail, String userPassword, AsyncResponse delegate, Context context)
    {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.delegate = delegate;
        this.baseUrl = baseUrl;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params)
    {

        try
        {
            dbh = new DatabaseHandler(context);
            rawLoginData = postLogin();
            Log.d("her er rawLogin ", rawLoginData);
            loginData = gson.fromJson(rawLoginData, LoginData.class);
            Token refreshToken = new Token("refreshToken", loginData.getRefreshToken());
            Token accessToken = new Token("accessToken", loginData.getAccessToken());
            dbh.addToken(refreshToken);
            dbh.addToken(accessToken);
            rawUser = getUser();
            currentUser = gson.fromJson(rawUser, User.class);

            List<Token> tokens = dbh.getAllTokens();
            for (Token cb : tokens) {
                String log = "Id: " + cb.getId() + " ,Name: " + cb.getName() + " ,tokenData: " + cb.getTokenData();
                // Writing CoffeeBrands to log
                Log.d("Name: ", log);
            }

            Log.d("her er usr: ", rawUser);
            currentUser.setLoginData(loginData);


            return gson.toJson(currentUser);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "WRONG PASSWORD OR EMAIL";
        }
    }

    @Override
    protected void onPostExecute(String result)
    {
        delegate.processFinished(result);
    }


    public String postLogin() throws IOException
    {
        InputStream input = null;
        OutputStream output = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = null;
        gson = new Gson();

        JsonObject jo = new JsonObject();
        jo.addProperty("email", userEmail);
        jo.addProperty("password", userPassword);
        Log.d("her er jo ", jo.toString());
        try
        {
            URL url = new URL("http://82.211.198.31:3000/" + "login");
            Log.d("full url: ", url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            output = connection.getOutputStream();
            Log.d("her er jo igen ", jo.toString());
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

}
