package com.elementsculmyca.ec19_app.UI.EventPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elementsculmyca.ec19_app.DataSources.DataModels.CoordinatorModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.EventDataModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.PrizeModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.TimingsModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.AppDatabase;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventLocalModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventsDao_Impl;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.UserDao_Impl;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.UserLocalModel;
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
    private TextView rulesHeading,coordinator1Name,coordinator1Phone,coordinator2Name,coordinator2Phone,contactTextView,prizeTextView,prize1Text,prize2Text,prize3Text;
    private int eventFee,eventHitCount;
    private String[] eventTags;
    private PrizeModel eventPrize;
    private TimingsModel eventTime;
    private TextView rules;
    LinearLayout prize1,prize2,prize3;
    EventsDao_Impl dao;
    EventLocalModel eventData;
    UserLocalModel user;
    UserDao_Impl daoUser;
    TextView registerButton;

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
        contactTextView = view.findViewById(R.id.tv_contact);
        prizeTextView = view.findViewById(R.id.tv_prizes);
        prize1 = view.findViewById(R.id.ll_prize1);
        prize2 = view.findViewById(R.id.ll_prize2);
        prize3 = view.findViewById(R.id.ll_prize3);
        prize1Text = view.findViewById(R.id.prize1);
        prize2Text = view.findViewById(R.id.prize2);
        prize3Text = view.findViewById(R.id.prize3);
        registerButton = getActivity().findViewById(R.id.register);
        apiInterface = ApiClient.getClient().create( ApiInterface.class );
        daoUser=new UserDao_Impl(AppDatabase.getAppDatabase(getActivity()));
        user = daoUser.getTicketbyId(eventId);
        if(user==null) {
            registerButton.setText("Register Now!");
        }
        else
            registerButton.setText("View Ticket");
        eventData =new EventLocalModel();
        eventData = dao.getEventByEventId(eventId);
        if(eventData.getRules().equals("")||eventData.getRules().equals("NA")||eventData.getRules().equals("Nil")) {
            rulesHeading.setVisibility(View.GONE);
            rules.setVisibility(View.GONE);
        }
        else
        rules.setText(eventData.getRules());
        List<String> coordinator = Arrays.asList(eventData.getCoordinator().split("%"));
        if(coordinator.get(0).equals("")&&coordinator.get(1).equals("null")&&coordinator.get(2).equals("")&&coordinator.get(3).equals("null"))
            contactTextView.setVisibility(View.GONE);
        if(coordinator.get(0).equals("")||coordinator.get(0).equals("null")){
            coordinator1Name.setVisibility(View.GONE);
        }else
        coordinator1Name.setText(coordinator.get(0));
        if(coordinator.get(1).equals("")||coordinator.get(1).equals("null")) {
            coordinator1Phone.setVisibility(View.GONE);
        }else
            coordinator1Phone.setText(" : " + coordinator.get(1));
        if(coordinator.get(2).equals("")||coordinator.get(2).equals("null")){
            coordinator2Name.setVisibility(View.GONE);
        }else
        coordinator2Name.setText(coordinator.get(2) + " : ");
        if(coordinator.get(3).equals("")||coordinator.get(3).equals("null")){
            coordinator2Phone.setVisibility(View.GONE);
        }else
        coordinator2Phone.setText(coordinator.get(3));

        Log.d("prerna",eventData.getPrizes());

        List<String> prizes = Arrays.asList(eventData.getPrizes().split("%"));
        if(prizes.size()==0||prizes.get(0).equals("null")||prizes.get(0).equals("0")){
            prizeTextView.setVisibility(View.GONE);
            prize1.setVisibility(View.GONE);
            prize2.setVisibility(View.GONE);
            prize3.setVisibility(View.GONE);
        }else if(prizes.size()==1||prizes.get(1).equals("null")||prizes.get(1).equals("0")){
            prize1Text.setText(prizes.get(0));
            prize2.setVisibility(View.GONE);
            prize3.setVisibility(View.GONE);
        }else if(prizes.size()==2||prizes.get(2).equals("null")||prizes.get(2).equals("0")){
            prize1Text.setText(prizes.get(0));
            prize2Text.setText(prizes.get(1));
            prize3.setVisibility(View.GONE);
        }else {
            prize1Text.setText(prizes.get(0));
            prize2Text.setText(prizes.get(1));
            prize3Text.setText(prizes.get(2));
        }
        return view;
    }

}
