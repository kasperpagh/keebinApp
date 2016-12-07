package kasper.pagh.keebin;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import entity.User;
import userReST.PostLogin;

public class LoginActivity extends AppCompatActivity implements AsyncResponse
{
    private Gson gson = new Gson();
    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = (EditText) findViewById(R.id.loginEmail);
        editTextPassword = (EditText) findViewById(R.id.loginPassword);
    }

    public void login(View view)
    {
//        JsonObject loginObject = new JsonObject();
//        loginObject.addProperty("email", editTextEmail.getText().toString());
//        loginObject.addProperty("password", editTextPassword.getText().toString());


//        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra(); Her kan vi ligge den uder der er logget ind!!
//        startActivity(intent);

        PostLogin postLogin = new PostLogin(getResources().getString(R.string.baseUrl), editTextEmail.getText().toString(), editTextPassword.getText().toString(), this);
        postLogin.execute();
    }

    @Override
    public void processFinished(String output)
    {
        Log.d("her er data fra login", output);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("unparsedCurrentUser", output);
        startActivity(intent);
    }
}
