package com.elementsculmyca.ec19_app.UI.HomePage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elementsculmyca.ec19_app.DataSources.DataModels.EventDataModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.TicketModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.AppDatabase;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.DatabaseInitializer;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventLocalModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventsDao_Impl;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiClient;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiInterface;
import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.ClubEventListPage.EventAdapter;
import com.elementsculmyca.ec19_app.UI.MyTicketsPage.TicketsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private ApiInterface apiInterface;
    RelativeLayout searchView;
    ViewPager viewPager;
    private EventAdapter eventAdapter;
    private ArrayList<ClubEventModel> allSampleData = new ArrayList<ClubEventModel>();
    private RecyclerView recyclerView;
    ArrayList<EventDataModel> eventList;
    Button day1,day2,day3;
    DatabaseInitializer databaseInitializer;
    ProgressBar bar;
    SharedPreferences sharedPreferences;
    String userName,userPhone;



    ViewPager.OnPageChangeListener onchange = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
            int x = viewPager.getCurrentItem();
            if(x==0){
                day1.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.day_background_orange));
                day2.setBackgroundColor(Color.parseColor("#d8d8d8"));
                day3.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.day_background_grey));
            }else if(x==1){
                day1.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.day_background_grey));
                day2.setBackgroundColor(Color.parseColor("#f5a623"));
                day3.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.day_background_grey));
            }else{
                day1.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.day_background_grey));
                day2.setBackgroundColor(Color.parseColor("#d8d8d8"));
                day3.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.day_background_orange));
            }

        }

        @Override
        public void onPageSelected(int i) {



        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate( R.layout.fragment_main_screen, container, false );
        viewPager= root.findViewById(R.id.days_viewpager);
        searchView=  root.findViewById(R.id.search_view);


        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Searchable.class);
                startActivity(intent);
            }
        });

        apiInterface = ApiClient.getClient().create( ApiInterface.class );
        sharedPreferences= this.getActivity().getSharedPreferences("login_details",0);
        userName = sharedPreferences.getString("Username","");
        userPhone = sharedPreferences.getString("UserPhone","");
        day1=root.findViewById(R.id.btn_day1);
        day2=root.findViewById(R.id.btn_day2);
        day3=root.findViewById(R.id.btn_day3);
        bar=root.findViewById(R.id.pb);
        viewPager.addOnPageChangeListener(onchange);
        DayAdapter adapterday= new DayAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapterday);
        day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0,true);

            }
        });
        day2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1,true);
            }
        });
        day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2,true);
            }
        });
        addData();
        EventCategoryAdapter adapter = new EventCategoryAdapter(getActivity(), allSampleData);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        RecyclerView categoryRecycleview = (RecyclerView) root.findViewById(R.id.categories_recyclerView);
        categoryRecycleview.setLayoutManager(linearLayoutManager);
        categoryRecycleview.setAdapter(adapter);
        return root;
    }

    private int getCurrItem() {
        return viewPager.getCurrentItem();
    }

    private void addData() {
        ClubEventModel manan = new ClubEventModel();
        manan.setClubName("Manan");
        manan.setDisplayName("Coding");
        allSampleData.add(manan);

        ClubEventModel srijan = new ClubEventModel();
        srijan.setClubName("Srijan");
        srijan.setDisplayName("Arts");
        allSampleData.add(srijan);

        ClubEventModel ananya = new ClubEventModel();
        ananya.setClubName("Ananya");
        ananya.setDisplayName("Lit-Deb");
        allSampleData.add(ananya);

        ClubEventModel vividha = new ClubEventModel();
        vividha.setClubName("Vividha");
        vividha.setDisplayName("Dramatics");
        allSampleData.add(vividha);

        ClubEventModel jhalak = new ClubEventModel();
        jhalak.setClubName("Jhalak");
        jhalak.setDisplayName("Photography & Designing");
        allSampleData.add(jhalak);

        ClubEventModel eklavya = new ClubEventModel();
        eklavya.setClubName("Eklavya");
        eklavya.setDisplayName("Fun Events");
        allSampleData.add(eklavya);

        ClubEventModel ieee = new ClubEventModel();
        ieee.setClubName("IEEE");
        ieee.setDisplayName("Techno Fun");
        allSampleData.add(ieee);

        ClubEventModel mechnext = new ClubEventModel();
        mechnext.setClubName("Mechnext");
        mechnext.setDisplayName("Mechanical");
        allSampleData.add(mechnext);

        ClubEventModel microbird = new ClubEventModel();
        microbird.setClubName("Microbird");
        microbird.setDisplayName("Electronics");
        allSampleData.add(microbird);

        ClubEventModel natraja = new ClubEventModel();
        natraja.setClubName("Nataraja");
        natraja.setDisplayName("Dance");
        allSampleData.add(natraja);

        ClubEventModel sae = new ClubEventModel();
        sae.setClubName("SAE");
        sae.setDisplayName("Automobiles");
        allSampleData.add(sae);

        ClubEventModel samarpan = new ClubEventModel();
        samarpan.setClubName("Samarpan");
        samarpan.setDisplayName("Electrical");
        allSampleData.add(samarpan);

        ClubEventModel tarannum = new ClubEventModel();
        tarannum.setClubName("Taranuum");
        tarannum.setDisplayName("Music");
        allSampleData.add(tarannum);

        ClubEventModel vivekanand = new ClubEventModel();
        vivekanand.setClubName("Vivekanand Manch");
        vivekanand.setDisplayName("Socio-Cultural");
        allSampleData.add(vivekanand);

        ClubEventModel niramayam = new ClubEventModel();
        niramayam.setClubName("Niramayam");
        niramayam.setDisplayName("Yoga");
        allSampleData.add(niramayam);
    }
}
