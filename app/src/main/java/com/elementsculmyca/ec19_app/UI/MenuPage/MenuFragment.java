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

import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.BookmarksPage.BookmarksFragment;
import com.elementsculmyca.ec19_app.UI.EventPage.RegisterEventFragment;
import com.elementsculmyca.ec19_app.UI.LoginScreen.LoginActivity;

public class MenuFragment extends Fragment {
    RelativeLayout bookmarks,hackon,xunbao,logout;
    SharedPreferences sharedPreferences;
    String phoneNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        bookmarks = root.findViewById(R.id.bookmarks);
        hackon = root.findViewById(R.id.hackon);
        xunbao  = root.findViewById(R.id.xunbao);
        logout = root.findViewById(R.id.logout);
        sharedPreferences= this.getActivity().getSharedPreferences("login_details",0);
        phoneNumber = sharedPreferences.getString("UserPhone","");
        if (phoneNumber.equals("")){
            logout.setVisibility(View.GONE);
        }
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

        bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.frame, new BookmarksFragment()).commit();
            }
        });

        return root;
    }
}
