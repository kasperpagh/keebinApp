package kasper.pagh.keebin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import rest.userReST.PostLogin;

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
        PostLogin postLogin = new PostLogin(getResources().getString(R.string.baseUrl), getResources().getString(R.string.loginString),editTextEmail.getText().toString(), editTextPassword.getText().toString(), this, getApplicationContext());
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
