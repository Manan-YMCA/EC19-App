package com.elementsculmyca.ec19_app.UI.MainScreen;


import android.content.Context;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.elementsculmyca.ec19_app.DataSources.DataModels.EventDataModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.AppDatabase;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.DatabaseInitializer;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiClient;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiInterface;
import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.BookmarksPage.BookmarksFragment;
import com.elementsculmyca.ec19_app.UI.DeveloperPage.DeveloperFragment;
import com.elementsculmyca.ec19_app.UI.HomePage.HomeFragment;
import com.elementsculmyca.ec19_app.UI.MenuPage.MenuFragment;
import com.elementsculmyca.ec19_app.UI.MyTicketsPage.MyTicketsFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainScreenActivity extends AppCompatActivity {
    ImageView home,bookmarks,tickets,developers,more;
    DatabaseInitializer databaseInitializer;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_screen );
        apiInterface = ApiClient.getClient().create( ApiInterface.class );
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
        home = findViewById(R.id.home);
        bookmarks = findViewById(R.id.bookmarks);
        tickets = findViewById(R.id.tickets);
        developers = findViewById(R.id.developers);
        more = findViewById(R.id.more);
        if(isNetworkAvailable()) {
            getAllEvents();
        }
        home.setColorFilter( ContextCompat.getColor( MainScreenActivity.this, R.color.Black), android.graphics.PorterDuff.Mode.MULTIPLY );
        bookmarks.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        tickets.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        developers.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        more.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToFragmentHome();
            }
        });

        bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToFragmentBookmarks();
            }
        });

        tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToFragmentTickets();
            }
        });

        developers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToFragmentDevelopers();
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToFragmentMenu();
            }
        });
    }

    void getAllEvents() {
        Call<ArrayList<EventDataModel>> call = apiInterface.getEventList();
        call.enqueue( new Callback<ArrayList<EventDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EventDataModel>> call, Response<ArrayList<EventDataModel>> response) {
                //TODO YAHAN PE LIST AAEGI API SE UI ME LAGA LENA
                try{
                    databaseInitializer.populateSync(AppDatabase.getAppDatabase(MainScreenActivity.this),response.body());
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ArrayList<EventDataModel>> call, Throwable t) {
            }

        } );

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void switchToFragmentHome() {
        home.setColorFilter( ContextCompat.getColor( MainScreenActivity.this, R.color.Black ), PorterDuff.Mode.MULTIPLY );
        bookmarks.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        tickets.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        developers.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        more.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
    }

    private void switchToFragmentBookmarks() {
        home.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        bookmarks.setColorFilter( ContextCompat.getColor( MainScreenActivity.this, R.color.Black ), android.graphics.PorterDuff.Mode.MULTIPLY );
        tickets.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        developers.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        more.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame, new BookmarksFragment()).commit();
    }

    private void switchToFragmentTickets() {
        home.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        bookmarks.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        tickets.setColorFilter( ContextCompat.getColor( MainScreenActivity.this, R.color.Black ), android.graphics.PorterDuff.Mode.MULTIPLY );
        developers.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        more.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame, new MyTicketsFragment()).commit();
    }

    private void switchToFragmentDevelopers() {
        home.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        bookmarks.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        tickets.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        developers.setColorFilter( ContextCompat.getColor( MainScreenActivity.this, R.color.Black), android.graphics.PorterDuff.Mode.MULTIPLY );
        more.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame, new DeveloperFragment()).commit();
    }

    private void switchToFragmentMenu() {
        home.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        bookmarks.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        tickets.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        developers.setColorFilter(ContextCompat.getColor(MainScreenActivity.this, R.color.Grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        more.setColorFilter( ContextCompat.getColor( MainScreenActivity.this, R.color.Black), android.graphics.PorterDuff.Mode.MULTIPLY );
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame, new MenuFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment f =  fragmentManager.findFragmentById(R.id.frame);
        if(f instanceof HomeFragment){
            super.onBackPressed();
        }else if(f instanceof BookmarksFragment){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.frame, new MenuFragment()).commit();
        }
        else{
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
        }
    }
}
