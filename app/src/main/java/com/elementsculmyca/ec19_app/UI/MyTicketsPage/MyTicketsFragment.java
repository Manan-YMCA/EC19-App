package com.elementsculmyca.ec19_app.UI.MyTicketsPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elementsculmyca.ec19_app.DataSources.DataModels.TicketModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.AppDatabase;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.DatabaseInitializer;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventsDao_Impl;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.UserDao_Impl;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.UserLocalModel;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiClient;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiInterface;
import com.elementsculmyca.ec19_app.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTicketsFragment extends Fragment {



    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    SharedPreferences sharedPreferences;
    private List<TicketsModel> ticketsDetails;
    TextView userName,userPhone;
    RelativeLayout relativeLayout;
    TextView login;
    UserDao_Impl dao;
    List<UserLocalModel> data;
    ProgressBar progressBar;
    ApiInterface apiInterface;
    TextView tickets;
    String phoneNumber;
    DatabaseInitializer databaseInitializer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tickets, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclertickets);
        userName = root.findViewById(R.id.tv_name);
        userPhone = root.findViewById(R.id.number);
        relativeLayout = root.findViewById(R.id.rl);
        login = root.findViewById(R.id.login);
        progressBar = root.findViewById(R.id.pb);
        tickets = root.findViewById(R.id.no_tickets);
        apiInterface = ApiClient.getClient().create( ApiInterface.class );
        dao=new UserDao_Impl(AppDatabase.getAppDatabase(getActivity()));
        sharedPreferences= this.getActivity().getSharedPreferences("login_details",0);
        userName.setText(sharedPreferences.getString("Username",""));
        phoneNumber = sharedPreferences.getString("UserPhone","");
        userPhone.setText(phoneNumber);
        if(userPhone.getText().equals("")){
            relativeLayout.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
        }else{
            relativeLayout.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
                data = dao.getAll();
                if(data.size() == 0&&!isNetworkAvailable())
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                else if(data.size()==0 &&isNetworkAvailable())
                    tickets.setVisibility(View.VISIBLE);
                else {
                    tickets.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    adapter = new TicketsAdapter(data, getActivity());
                    recyclerView.setAdapter(adapter);
                }
        }


        return root;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
