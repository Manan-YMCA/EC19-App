package com.elementsculmyca.ec19_app.UI.MainScreen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.BookmarksPage.BookmarksFragment;
import com.elementsculmyca.ec19_app.UI.DeveloperPage.DeveloperFragment;
import com.elementsculmyca.ec19_app.UI.HomePage.HomeFragment;
import com.elementsculmyca.ec19_app.UI.MenuPage.MenuFragment;
import com.elementsculmyca.ec19_app.UI.MyTicketsPage.MyTicketsFragment;
import com.elementsculmyca.ec19_app.UI.aboutPage.AboutBaseFragment;

import java.util.List;

public class MainScreenActivity extends AppCompatActivity {
    ImageView home,about,tickets,developers,more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_screen );
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
        home = findViewById(R.id.home);
        about = findViewById(R.id.about);
        tickets = findViewById(R.id.tickets);
        developers = findViewById(R.id.developers);
        more = findViewById(R.id.more);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToFragmentHome();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToFragmentAbout();
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
    public void switchToFragmentHome() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
    }

    private void switchToFragmentAbout() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame, new AboutBaseFragment(),"ABOUT_FRAGMENT").commit();
    }

    private void switchToFragmentTickets() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame, new MyTicketsFragment()).commit();
    }

    private void switchToFragmentDevelopers() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame, new DeveloperFragment()).commit();
    }

    private void switchToFragmentMenu() {
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
