package com.elementsculmyca.ec19_app.UI.SplashScreen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.elementsculmyca.ec19_app.DataSources.DataModels.CoordinatorModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.EventDataModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.PrizeModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.TimingsModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.AppDatabase;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.DatabaseInitializer;
import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.LoginScreen.LoginActivity;
import com.elementsculmyca.ec19_app.UI.MainScreen.MainScreenActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SplashScreenActivity extends Activity {
    public static int SPLASH_TIME_OUT = 3000;
    SharedPreferences sharedPreferences;
    ArrayList<EventDataModel> allEvents;
    private IncomingHandler incomingHandler;
    DatabaseInitializer databaseInitializer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        incomingHandler = new IncomingHandler(SplashScreenActivity.this);
        allEvents = new ArrayList<>();
        sharedPreferences=getSharedPreferences("login_details",0);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                String phone = sharedPreferences.getString("UserPhone","");
                if(phone.equals("")) {
                    Intent SplashScreen = new Intent(SplashScreenActivity.this, LoginActivity.class).putExtra("check","0");
                    startActivity(SplashScreen);
                }
                else {
                    startActivity(new Intent(SplashScreenActivity.this,MainScreenActivity.class));
                }
                finish();
            }
        },SPLASH_TIME_OUT);

    }

    @Override
    protected void onResume() {
        retreiveEventsHardCode();
        super.onResume();
    }

    void retreiveEventsHardCode() {
        InputStream is = getResources().openRawResource(R.raw.allevents);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String jsonString = writer.toString();
        JsonParse(jsonString);
    }

    void JsonParse(String response) {
        try {
            JSONObject object = new JSONObject(response);
            JSONArray eventArray = object.getJSONArray("data");
            for (int i = 0; i < eventArray.length(); i++) {
                EventDataModel event = new EventDataModel();
                JSONObject currEvent = eventArray.getJSONObject(i);
                if (currEvent.has("title"))
                    event.setTitle(currEvent.getString("title"));
                Log.d("prerna",event.getTitle());
                if (currEvent.has("fee")) {
                    if(currEvent.getString("fee").equals("null"))
                        event.setFee(0);
                    else
                    event.setFee(currEvent.getInt("fee"));
                }
                if (currEvent.has("timing")) {
                    JSONObject timing = currEvent.getJSONObject("timing");
                    TimingsModel timingsModel = new TimingsModel();
                    if (timing.has("from"))
                        timingsModel.setFrom(timing.getLong("from"));
                    if (timing.has("to"))
                        timingsModel.setTo(timing.getLong("to"));
                    event.setTime(timingsModel);
                }
                if (currEvent.has("clubname"))
                    event.setClubname(currEvent.getString("clubname"));
                if (currEvent.has("category"))
                    event.setCategory(currEvent.getString("category"));
                if (currEvent.has("desc"))
                    event.setDesc(currEvent.getString("desc"));
                if (currEvent.has("venue"))
                    event.setVenue(currEvent.getString("venue"));
                if (currEvent.has("rules"))
                    event.setRules(currEvent.getString("rules"));
                if (currEvent.has("photolink")) {
                    event.setPhotolink(currEvent.getString("photolink"));
                } else {
                    event.setPhotolink(null);
                }
                event.setCoordinatorModelList(new ArrayList<CoordinatorModel>());
                if (currEvent.has("coordinators")) {
                    JSONArray coordinators = currEvent.getJSONArray("coordinators");
                    for (int j = 0; j < coordinators.length(); j++) {
                        JSONObject coordinatorsDetail = coordinators.getJSONObject(j);
                        CoordinatorModel coord = new CoordinatorModel();
                        if (coordinatorsDetail.has("phone")) {
                            if(coordinatorsDetail.getString("phone").equals("null"))
                                coord.setPhone(Long.parseLong("0"));
                         else
                            coord.setPhone(coordinatorsDetail.getLong("phone"));
                        }
                        if (coordinatorsDetail.has("name"))
                            coord.setName(coordinatorsDetail.getString("name"));
                        event.getCoordinatorModelList().add(coord);
                    }
                }
                if (currEvent.has("prizes")) {
                    JSONObject prize = currEvent.getJSONObject("prizes");
                    PrizeModel prizeModel = new PrizeModel();
                    if (prize.has("prize1"))
                        prizeModel.setPrize1(prize.getString("prize1"));
                    if (prize.has("prize2"))
                        prizeModel.setPrize2(prize.getString("prize2"));
                    if (prize.has("prize3"))
                        prizeModel.setPrize3(prize.getString("prize3"));
                    event.setPrizes(prizeModel);
                } else {
                   PrizeModel prizeModel = new PrizeModel();
                   prizeModel.setPrize1(null);
                   prizeModel.setPrize2(null);
                   prizeModel.setPrize3(null);
                   event.setPrizes(prizeModel);
                }
                if (currEvent.has("_id"))
                    event.setId(currEvent.getString("_id"));
                if (currEvent.has("eventtype")) {
                    event.setEventType(currEvent.getString("eventtype"));

                }

                allEvents.add(event);
                Log.d("prerna",Integer.toString(allEvents.size()));
            }
            incomingHandler.sendEmptyMessage(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class IncomingHandler extends Handler {
        private WeakReference<SplashScreenActivity> yourActivityWeakReference;

        IncomingHandler(SplashScreenActivity yourActivity) {
            yourActivityWeakReference = new WeakReference<>(yourActivity);
        }

        @Override
        public void handleMessage(Message message) {
            if (yourActivityWeakReference != null) {
                SplashScreenActivity yourActivity = yourActivityWeakReference.get();

                switch (message.what) {
                    case 0:
                        updateDatabase();
                        break;
                }
            }
        }

        private void updateDatabase() {
            //Log.d("prerna",Integer.toString(allEvents.size()));
            try {
                databaseInitializer.populateSync(AppDatabase.getAppDatabase(SplashScreenActivity.this), allEvents);
            }catch (Exception e){

            }
        }
    }
}
