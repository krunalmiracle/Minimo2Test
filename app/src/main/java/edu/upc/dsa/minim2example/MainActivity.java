package edu.upc.dsa.minim2example;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.upc.dsa.minim2example.models.Element;
import edu.upc.dsa.minim2example.models.Museums;
import edu.upc.dsa.minim2example.models.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    // RETROFIT OBJECT
    private static Retrofit retrofit;

    //MUSEUM SERVICE OBJECT
    MuseumService museumService;


    //List<Repo> Repo_List;
    List<Element> elementList ;
    User user = new User();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    //As we added new methods inside our custom Adapter, we need to create our own type of adapter
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //After launch check if a user exists in preferences and also set the player
        if(!ExistUserLogged()){
            LaunchLoginActivity();
        }else{
            LoginUser();
        }
    }
    private void LoginUser() {
        String username, password;
        username = user.getUsername();
        password = user.getPassword();
        if (username.equals("user") || password.equals("dsamola")) {
            NotifyUser("Automaically logged");
            startRetrofit();
            museumService = retrofit.create(MuseumService.class);
            getMuseums();
        } else {
            NotifyUser("Something went wrong");
        }
    }
    private static void startRetrofit(){
        //HTTP &
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Attaching Interceptor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();

        // Running Retrofit to get result from Local tracks service Interface
        //Remember when using Local host on windows the IP is 10.0.2.2 for Android
        //Also added NullOnEmptyConverterFactory when the response from server is empty
        retrofit = new Retrofit.Builder()
                .baseUrl("https://do.diba.cat/api/dataset/museus/format/json/pag-ini/1/pag-fi/15")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
    private void getMuseums(){
        //Retrofit Implementation on Button Press
        //Adding Interceptor
        try {

            Call<Museums> museumsCall = museumService.getMuseums();
            /* Android Doesn't allow synchronous execution of Http Request and so we must put it in queue*/
            museumsCall.enqueue(new Callback<Museums>() {
                @Override
                public void onResponse(Call<Museums> call, Response<Museums> response) {

                    //Retrieve the result containing in the body
                    if (response.isSuccessful()) {
                        Museums museums = response.body();
                        elementList=museums.getElements();
                        //If clicked once then new player list else update the recyclerview
                        if(mAdapter == null){
                            buildRecyclerView();
                        }else{
                            mAdapter = null;
                            buildRecyclerView();
                        }
                    } else {
                        // empty response...
                        Log.d("MuseumsList","Request Failed!");
                    }
                }

                @Override
                public void onFailure(Call<Museums> call, Throwable t) {
                    NotifyUser("Error,could not retrieve data!");
                }
            });
        }
        catch(Exception e){
            Log.d("MueumsList","Exception: " + e.toString());
        }
    }
    //Builds the RecyclerView
    private void buildRecyclerView(){
        //Implementing RecyclerView
        recyclerView = findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(false);
        // use a linear layout manager
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(elementList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LaunchMusumDetailActivity(elementList.get(position));
            }
        });
    }
    private boolean ExistUserLogged(){
        //Access the shared preference UserInfo and obtain the parameters, default =string empty
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        user.setUsername(settings.getString("Username", ""));
        user.setPassword(settings.getString("Password", ""));
        return !user.getUsername().equals("");
    }
    //User Notifier Handler using Toast
    private void NotifyUser(String MSG){
        Toast toast = Toast.makeText(MainActivity.this,MSG,Toast.LENGTH_SHORT);
        toast.show();
    }
    private void LaunchMusumDetailActivity(Element museum) {
        Intent intent = new Intent(MainActivity.this ,MuseumDetailActivity.class);
        intent.putExtra("Museum", (Parcelable) museum);
        startActivityForResult(intent,2);
    }
    private void LaunchLoginActivity() {
        Intent intent = new Intent(MainActivity.this ,LoginActivity.class);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If login activity closed means the user has logged in
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if(data!=null){
                user = data.getParcelableExtra("User");
                LoginUser();
                Log.w("s", "Test");}
                else{
                    NotifyUser("Could not log in");
                    LaunchLoginActivity();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                finish();
            }

        }
        if(requestCode==2){

        }
    }

}

