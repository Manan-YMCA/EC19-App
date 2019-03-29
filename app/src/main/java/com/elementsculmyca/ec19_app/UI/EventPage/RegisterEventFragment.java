package com.elementsculmyca.ec19_app.UI.EventPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.PendingIntent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Patterns;
import android.content.Intent;

import com.elementsculmyca.ec19_app.DataSources.DataModels.ResponseModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.TicketModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.AppDatabase;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.DatabaseInitializer;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventLocalModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventsDao_Impl;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiClient;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiInterface;
import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.MyTicketsPage.TicketsAdapter;
import com.elementsculmyca.ec19_app.Util.TicketsGenerator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.ProgressDialog.show;
import static android.support.v4.content.ContextCompat.getSystemService;


public class RegisterEventFragment extends Fragment {


    private ApiInterface apiInterface;
    EditText userName,userEmail;
    EditText userClg,name,phone,userPhone;
    String intentTeam;
    String intentEmail;
    String intentPhone;
    String intentClg;
    String uname;
    String intentName;
    Button bt,saveForLater;
    ArrayList<TextView> memberno;
    ArrayList<EditText> nameText, phoneText;
    Button addButton;
    LinearLayout addMembers;
    String eventName,eventId;
    Long timestamp;
    int count=1;
    String qrcode;
    JSONArray team;
    SharedPreferences sharedPreferences;
    ProgressBar pb;
    LinearLayout parentLayout;
    DatabaseInitializer databaseInitializer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        eventName= getArguments().getString("eventName");
        eventId = getArguments().getString("eventId");
        sharedPreferences= this.getActivity().getSharedPreferences("login_details",0);

        View view = inflater.inflate( R.layout.fragment_register_event, container, false );



        apiInterface = ApiClient.getClient().create( ApiInterface.class );
        userName=(EditText) view.findViewById(R.id.user_name);
        userPhone = view.findViewById(R.id.phone_number);
        userClg=(EditText) view.findViewById(R.id.clg);
        userEmail=(EditText) view.findViewById(R.id.useremail) ;
        bt = (Button) view.findViewById(R.id.registerNow);
        saveForLater = view.findViewById(R.id.save);
        addMembers = view.findViewById(R.id.ll_add);
        parentLayout = view.findViewById(R.id.parent_layout);
        memberno = new ArrayList<>();
        pb = view.findViewById(R.id.pb);
        nameText = new ArrayList<>();
        phoneText = new ArrayList<>();
        team = new JSONArray();
        nameText.add(userName);
        phoneText.add(userPhone);
        addButton=view.findViewById(R.id.add_mem);
        EventsDao_Impl dao;
        EventLocalModel eventData;
        dao=new EventsDao_Impl(AppDatabase.getAppDatabase(getActivity()));
        eventData =new EventLocalModel();
        eventData = dao.getEventByEventId(eventId);
        if(eventData.getEventType().equals("solo"))
            addMembers.setVisibility(View.GONE);
        final LinearLayout layout = view.findViewById(R.id.layout_infater);
        userName.setText(sharedPreferences.getString("Username",""));
        userClg.setText(sharedPreferences.getString("UserClg",""));
        userPhone.setText(sharedPreferences.getString("UserPhone",""));
        userEmail.setText(sharedPreferences.getString("UserEmail",""));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View v = LayoutInflater.from(getActivity()).inflate(R.layout.add_member_layout, layout, false);
                name = (EditText) v.findViewById(R.id.memname);
                phone = (EditText) v.findViewById(R.id.memphone);
                final Button remove = v.findViewById(R.id.remove_btn);
                final TextView tv_2 =  v.findViewById(R.id.member_no_count);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewGroup p1 = (ViewGroup) v.getParent();
                        ViewGroup p2 = (ViewGroup) p1.getParent();
                        ViewGroup p3 = (ViewGroup) p2.getParent();
                        ViewGroup p4 = (ViewGroup)p3.getParent();
                        Integer remove_member = Integer.parseInt(tv_2.getText().toString());

                        nameText.remove(remove_member - 1);
                        phoneText.remove(remove_member - 1);
                        memberno.remove(tv_2);
                        update();
                        p4.removeView(p3);
                        count--;
                    }
                });
                count++;
                tv_2.setText(String.valueOf(count));
                memberno.add(tv_2);
                nameText.add(name);
                phoneText.add(phone);

                layout.addView(v);
            }


        });

        saveForLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor= sharedPreferences.edit();
                Set<String> set = sharedPreferences.getStringSet("bookmarks", new HashSet<String>());
                if(set.isEmpty()) {
                    Set<String> newSet = new HashSet<String>();
                    newSet.add(eventId);
                    editor.putStringSet("bookmarks", newSet);
                    editor.commit();
                }else {
                    set.add(eventId);
                    editor.putStringSet("key", set);
                    editor.commit();
                }
                Toast.makeText(getActivity(), "Event Bookmarked", Toast.LENGTH_SHORT).show();
            }
        });


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentName = "";
                intentClg = "";
                intentTeam="";
                intentEmail="";
                intentPhone = "";
                Long tsLong = System.currentTimeMillis()/1000;
                timestamp = tsLong;
                uname = nameText.get(0).getText().toString();
                intentPhone += userPhone.getText().toString();
               intentEmail+= userEmail.getText().toString();

                for (int i = 0; i < nameText.size(); i++) {
                    JSONObject jo = new JSONObject();
                    try {
                        jo.put("name", nameText.get(i).getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        jo.put("phone", Double.parseDouble(phoneText.get(i).getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    team.put(jo);
                }

                intentClg = userClg.getText().toString();

//
                Boolean checker = validateCredentials();
                if (checker) {
                    pb.setVisibility(View.VISIBLE);
                    parentLayout.setVisibility(View.GONE);
                    registerEvent();
                }
            }

        });
        return view;
    }
    private void update() {
        for (int i = 0; i < memberno.size(); i++) {
            memberno.get(i).setText(String.valueOf(i + 2));
        }
    }
    void registerEvent() {
        Call<ResponseModel> call = apiInterface.postregisterEvent( uname+"", intentPhone+"", intentEmail+"",
                intentClg+"", eventId+"", eventName, team ,timestamp );
        call.enqueue( new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                //TODO YAHAN PE EVENT KA DATA AAEGA API SE UI ME LAGA LENA
                Log.e( "Response", response.body().getStatus() + "" );
                Log.e("Response",response.body().getQrcode() + "");
                qrcode = response.body().getQrcode();
                if(response.body().getStatus().equals("Success")){
                    getAllTickets();
                    TicketFragment ticketFragment = new TicketFragment ();
                    Bundle args = new Bundle();
                    args.putString("qrcode", qrcode);
                    ticketFragment.setArguments(args);
                    pb.setVisibility(View.GONE);
                    //Inflate the fragment
                    getFragmentManager().beginTransaction().add(R.id.frame, ticketFragment).remove(RegisterEventFragment.this).commit();


                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e( "prerna", call.request().url() + "" + call.request().body() );
            }
        } );
    }

    void getAllTickets(){
        Call<ArrayList<TicketModel>> call = apiInterface.getTickets(sharedPreferences.getString("UserPhone",""));
        call.enqueue( new Callback<ArrayList<TicketModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TicketModel>> call, Response<ArrayList<TicketModel>> response) {
                //TODO YAHAN PE LIST AAEGI API SE UI ME LAGA LENA
                ArrayList<TicketModel> ticketList= response.body();
                Log.d("Response",Integer.toString(ticketList.size()));
                databaseInitializer.populateTicketSync(AppDatabase.getAppDatabase(getActivity()),ticketList);;
            }

            @Override
            public void onFailure(Call<ArrayList<TicketModel>> call, Throwable t) {

            }

        } );

    }



    private Boolean validateCredentials() {



        for (EditText nameTextView : nameText) {
            if (nameTextView.getText().toString().equals("")) {
                nameTextView.setError("Enter a User Name");
                return false;
            }
        }
        for (EditText collegeTextView : phoneText) {
            if (collegeTextView.getText().toString().equals("")) {
                collegeTextView.setError("Enter a Phone Number");
                return false;
            }
        }

        if (userPhone.getText().toString().equals("")) {
            userPhone.setError("Enter a Phone Number");
            return false;
        }
        if (!Patterns.PHONE.matcher(userPhone.getText().toString()).matches()) {
            userPhone.setError("Enter a valid Phone Number");
            return false;
        }
        if (userPhone.getText().toString().length() != 10) {
            userPhone.setError("Enter a valid Phone Number");
            return false;
        }
        if(userEmail.getText().toString().equals("")){
            userEmail.setError("Enter a email address");
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString()).matches()){
            userEmail.setError("Enter a valid email address");
            return false;
        }
        return true;
    }



}