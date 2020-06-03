package edu.upc.dsa.minim2example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.upc.dsa.minim2example.models.User;

public class LoginActivity extends AppCompatActivity{

    private EditText username;
    private EditText password;
    private User user;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username= (EditText) findViewById(R.id.editUsername);
        password= (EditText) findViewById(R.id.editPassword);

        preferences =  LoginActivity.this.getPreferences(Context.MODE_PRIVATE);

        String user = preferences.getString("user","incorrect");
        String password = preferences.getString("dsamola","incorrect");

        if(user.equals("user")&&password.equals("dsamola"))
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    //User Notifier Handler using Toast
    private void NotifyUser(String MSG){
        Toast toast = Toast.makeText(LoginActivity.this,MSG,Toast.LENGTH_SHORT);
        toast.show();
    }

    public void logbuttonfunction(final View view) {
        String name = username.getText().toString();
        String pass = password.getText().toString();

        if (name.equals("user") && pass.equals("dsamola")) {

            SharedPreferences sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(username.getText().toString(), username.getText().toString());
            editor.putString(password.getText().toString(), password.getText().toString());
            editor.commit();

            Intent returnIntent = new Intent();
            returnIntent.putExtra("User",user);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
        else{
            NotifyUser("Unauthorized");
        }

    }
}
