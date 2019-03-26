package com.elementsculmyca.ec19_app.UI.EventPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.elementsculmyca.ec19_app.DataSources.DataModels.CoordinatorModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.EventDataModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.PrizeModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.TimingsModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.AppDatabase;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventLocalModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventsDao_Impl;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiClient;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiInterface;
import com.elementsculmyca.ec19_app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DescriptionEventFragment extends Fragment{
    private ApiInterface apiInterface;

    private String eventId;
    private TextView rulesHeading,coordinator1Name,coordinator1Phone,coordinator2Name,coordinator2Phone;
    private int eventFee,eventHitCount;
    private String[] eventTags;
    private PrizeModel eventPrize;
    private TimingsModel eventTime;
    private TextView rules;
    EventsDao_Impl dao;
    EventLocalModel eventData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        eventId = getArguments().getString("eventId");
        dao=new EventsDao_Impl(AppDatabase.getAppDatabase(getActivity()));
        View view = inflater.inflate(R.layout.fragment_event_description, container, false);
        rulesHeading = view.findViewById(R.id.tv_rules);
        rules = view.findViewById(R.id.rules);
        coordinator1Name = view.findViewById(R.id.coordinator1_name);
        coordinator1Phone = view.findViewById(R.id.coordinator1_phone);
        coordinator2Name = view.findViewById(R.id.coordinator2_name);
        coordinator2Phone = view.findViewById(R.id.coordinator2_phone);
        apiInterface = ApiClient.getClient().create( ApiInterface.class );
        //Toast.makeText(getActivity(), eventId, Toast.LENGTH_SHORT).show();
        eventData =new EventLocalModel();
        eventData = dao.getEventByEventId(eventId);
        if(eventData.getRules().equals("")||eventData.getRules().equals("NA")) {
            rulesHeading.setVisibility(View.GONE);
            rules.setVisibility(View.GONE);
        }
        else
        rules.setText(eventData.getRules());

        PrizeModel prizes= new PrizeModel();
        List<String> prizeList = Arrays.asList(eventData.getPrizes().split("%"));
        if(prizeList.size()==1){
            prizes.setPrize1(prizeList.get(0));
        }else if(prizeList.size()==2){
            prizes.setPrize1(prizeList.get(0));
            prizes.setPrize2(prizeList.get(1));
        }else if(prizeList.size()==3){
            prizes.setPrize1(prizeList.get(0));
            prizes.setPrize2(prizeList.get(1));
            prizes.setPrize3(prizeList.get(2));
        }
        Log.d("prerna",eventData.getCoordinator());
        List<String> coordinator = Arrays.asList(eventData.getCoordinator().split("%"));
        if(coordinator.size()==0){
            coordinator1Name.setVisibility(View.GONE);
            coordinator1Phone.setVisibility(View.GONE);
            coordinator2Name.setVisibility(View.GONE);
            coordinator2Phone.setVisibility(View.GONE);
        }else if(coordinator.size()==2){
            coordinator1Name.setText(coordinator.get(0) + " : ");
            coordinator1Phone.setText(coordinator.get(1));
            coordinator2Name.setVisibility(View.GONE);
            coordinator2Phone.setVisibility(View.GONE);
        }else if(coordinator.size()==4){
            coordinator1Name.setText(coordinator.get(0) + " : ");
            coordinator1Phone.setText(coordinator.get(1));
            coordinator2Name.setText(coordinator.get(2) + " : ");
            coordinator2Phone.setText(coordinator.get(3));
        }

        return view;
    }

}
