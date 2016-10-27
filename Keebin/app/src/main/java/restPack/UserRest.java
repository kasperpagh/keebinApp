package restPack;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import entity.User;
import kasper.pagh.keebin.AsyncResponse;


/**
 * Created by kasper on 2016-10-26.
 * Handles calls to the backend for information concerning users (and possibly login)
 */

public class UserRest extends AsyncTask<String, Void, String>
{

    private Gson gson;
    private String baseUrl;
    public AsyncResponse delegate = null;

    public UserRest()
    {
        gson = new Gson();
        //REMEBER TO CHANGE THIS TO A VALUE IN res/values/strings.xml - ALSO MUST BE UPDATED FOR PRODUCTION
        baseUrl = "localhost:3000/api";

    }

    @Override
    protected String doInBackground(String... params)
    {
        if (params[0].equals("1"))
        {
            try
            {
                putUser(params[1], params[2]);
            } catch (IOException e)
            {
                //some logic
            }

        }
        if (params[0].equals("2"))
        {
            try
            {
                deleteUser(params[1]);
            } catch (IOException e)
            {
                //some logic
            }

        }
        if (params[0].equals("3"))
        {

        }
        if (params[0].equals("4"))
        {

        }


        return null;
    }

    //This is the way to get the output/result to the activity!
    @Override
    protected void onPostExecute(String result)
    {
        delegate.processFinished(result);
    }

    //Returns the new representation of the user. METHOD NO 1 CALL WITH FIRST PARAM BEING "1",
    //THE SECOND BEING THE JSON REPRESENTATION OF THE NEW USER OBJECT AND THE THIRD BEING THE USER EMAIL
    private User putUser(String jsonUserToSave, String userEmail) throws IOException
    {
        InputStream input = null;
        OutputStream output = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = null;

        try
        {
            URL url = new URL(baseUrl + "/user/" + userEmail);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            output = connection.getOutputStream();
            output.write(jsonUserToSave.getBytes("UTF-8"));
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
            return gson.fromJson(sb.toString(), User.class);
        } finally
        {
            if (input != null)
            {
                input.close();
            }
        }
    }

    //METOD NUMBER 2, SECOND PARAM MUST BE THE EMAIL OF THE USER THAT YOU WISH TO DELETE

    private boolean deleteUser(String userEmail) throws IOException
    {

        URL url = new URL(baseUrl + "/user/" + userEmail);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);

        connection.connect();

        if (connection.getResponseCode() == 200)
        {
            return true;
        } else
        {
            return false;
        }

    }

    //METHOD NUMER 3, SECOND PARAM MUST BE JSON REPRESENTATION OF THE NEW USER TO BE REGISTERED.
    //ALSO I THINK THIS SHOULD RETURN THE USER (idk can be done without, seems a little dumb tho).
    private boolean createUser(String jsonNewUser) throws IOException
    {
        OutputStream output = null;

        URL url = new URL(baseUrl + "/user/new");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        output = connection.getOutputStream();
        output.write(jsonNewUser.getBytes("UTF-8"));
        output.close();

        connection.connect();

        if (connection.getResponseCode() == 200)
        {
            return true;
        } else
        {
            return false;
        }

    }


}
