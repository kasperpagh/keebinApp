package userReST;
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

import entity.User;
import kasper.pagh.keebin.AsyncResponse;


public class PutUser extends AsyncTask<String, Void, String>
{

    private User user;
    public AsyncResponse delegate = null;
    private String baseUrl;
    private Gson gson;

    public PutUser(User user, AsyncResponse delegate)
    {

        Log.d("hej fra user og user:", user.toString());
        this.user = user;
        this.delegate = delegate;
        this.baseUrl = baseUrl;
        gson = new Gson();
    }


    private String putUser() throws IOException
    {
        JsonObject jo = new JsonObject();
        jo.addProperty("firstName", user.getFirstName());
        jo.addProperty("lastName", user.getLastName());
        jo.addProperty("email", user.getEmail());
        jo.addProperty("birthday", user.getBirthday());
        jo.addProperty("sex", user.getSex());
        jo.addProperty("password", "test");
        jo.addProperty("role", 1);

        InputStream input = null;
        OutputStream output = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = null;

        try
        {
            Log.d("vi har prøvet noget :O", user.toString());
            URL url = new URL("http://192.168.0.11:3000/api/" + "users/user/" + user.getEmail());
            Log.d("full url: ", url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("refreshToken", user.getRefreshToken());
            output = connection.getOutputStream();
            output.write(gson.toJson(jo).getBytes("UTF-8"));
            output.close();
            Log.d("når vi her ned`?", user.toString());
            connection.connect();
            Log.d("connecter og hvad så?", user.toString());
            input = connection.getInputStream();
            Log.d("hvad med her?", user.toString());
            bufferedReader = new BufferedReader(new InputStreamReader(input));
            sb = new StringBuilder();
            String line;
            Log.d("Sut vi er langt:", user.toString());
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
            return putUser();
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

