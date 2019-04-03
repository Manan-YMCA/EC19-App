package com.elementsculmyca.ec19_app.UI.MenuPage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.elementsculmyca.ec19_app.DataSources.LocalServices.AppDatabase;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventsDao_Impl;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.UserDao_Impl;
import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.BookmarksPage.BookmarksFragment;
import com.elementsculmyca.ec19_app.UI.EventPage.RegisterEventFragment;
import com.elementsculmyca.ec19_app.UI.LoginScreen.LoginActivity;
import com.elementsculmyca.ec19_app.UI.aboutPage.AboutActivity;

public class MenuFragment extends Fragment {
    RelativeLayout about,hackon,xunbao,logout;
    SharedPreferences sharedPreferences;
    String phoneNumber;
    TextView login;
    UserDao_Impl usersDao;
    EventsDao_Impl eventsDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        about = root.findViewById(R.id.about);
        hackon = root.findViewById(R.id.hackon);
        xunbao  = root.findViewById(R.id.xunbao);
        login = root.findViewById(R.id.tv_login);
        eventsDao=new EventsDao_Impl(AppDatabase.getAppDatabase(getActivity()));
        usersDao=new UserDao_Impl(AppDatabase.getAppDatabase(getActivity()));
        logout = root.findViewById(R.id.logout);
        sharedPreferences= this.getActivity().getSharedPreferences("login_details",0);
        phoneNumber = sharedPreferences.getString("UserPhone","");
        if (phoneNumber.equals("")){
            logout.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
        }
        else {
            login.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),LoginActivity.class).putExtra("check","1"));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to log out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.commit();
                                usersDao.deleteAll();
                                getActivity().finish();
                                startActivity(new Intent(getActivity(),LoginActivity.class));

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AboutActivity.class));
            }
        });

        return root;
    }
}
