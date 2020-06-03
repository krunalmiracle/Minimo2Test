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

        preferences = getSharedPreferences("UserInfo", 0);
        editor = preferences.edit();

        String user = preferences.getString("user","incorrect");
        String password = preferences.getString("dsamola","incorrect");

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

            SharedPreferences settings = getSharedPreferences("UserInfo", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("Username", name);//Changed Explain!!
            editor.putString("Password", pass);//Changed Explain!!
            editor.commit();
            user = new User(name,pass);
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
