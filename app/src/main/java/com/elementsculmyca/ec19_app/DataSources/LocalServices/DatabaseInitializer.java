package com.elementsculmyca.ec19_app.DataSources.LocalServices;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.elementsculmyca.ec19_app.DataSources.DataModels.CoordinatorModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.EventDataModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.TicketModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db, List<EventDataModel> data) {
        PopulateDbAsync task = new PopulateDbAsync( db, data );
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db, List<EventDataModel> data) {
        populateWithData( db, data );
    }

    public static void populateTicketAsync(@NonNull final AppDatabase db, List<TicketModel> data) {
        PopulateTicketDbAsync task = new PopulateTicketDbAsync( db, data );
        task.execute();
    }

    public static void populateTicketSync(@NonNull final AppDatabase db, List<TicketModel> data) {
        populateWithTicketData( db, data );
    }

    private static EventLocalModel addUser(final AppDatabase db, EventLocalModel eventDataItem) {
        db.eventsDao().insertAll( eventDataItem );
        return eventDataItem;
    }

    private static void populateWithData(AppDatabase db, List<EventDataModel> data) {

        db.eventsDao().deleteAll();
        for (int i = 0; i < data.size(); i++) {
            String day;
            Long time = data.get( i ).getTime().getFrom();
            SimpleDateFormat formatter = new SimpleDateFormat( "dd/MM/yyyy" );
            String dateString = formatter.format( new Date( time ) );
            if (dateString.equals( "11/04/2019" ))
                day = "1";
            else if (dateString.equals( "12/04/2019" ))
                day = "2";
            else if (dateString.equals( "13/04/2019" ))
                day = "3";
            else
                day = "4";
            List<CoordinatorModel> coordinatorModelList = data.get( i ).getCoordinatorModelList();
            String coordinator = coordinatorModelList.get( 0 ).getName() + "%" + coordinatorModelList.get( 0 ).getPhone() + "%" + coordinatorModelList.get( 1 ).getName() + "%" + coordinatorModelList.get( 1 ).getPhone();
            EventLocalModel mdData = new EventLocalModel( data.get( i ).getId(),
                    data.get( i ).getTitle() + "",
                    data.get( i ).getClubname() + "",
                    data.get( i ).getCategory() + "",
                    data.get( i ).getDesc() + "",
                    data.get( i ).getRules() + "",
                    data.get( i ).getVenue() + "",
                    data.get( i ).getPhotolink() + "",
                    data.get( i ).getFee(),
                    data.get( i ).getTime().getFrom(),
                    data.get( i ).getTime().getTo(),
                    coordinator,
                    data.get( i ).getPrizes().getPrize1() + "%" + data.get( i ).getPrizes().getPrize2() + "%" + data.get( i ).getPrizes().getPrize3(),
                    data.get( i ).getEventType() + "",
                    "",
                    data.get( i ).getHitcount(),
                    day );

            addUser( db, mdData );
        }

        List<EventLocalModel> artList = db.eventsDao().getAll();
        Log.d( DatabaseInitializer.TAG, "Rows Count: " + artList.size() );
    }


    private static void populateWithTicketData(AppDatabase db, List<TicketModel> data) {
        db.userDao().deleteAll();

        for (int i = 0; i < data.size(); i++) {

            UserLocalModel userLocalModel = new UserLocalModel(
                    data.get( 0 ).getId() + "",
                    data.get( 0 ).getArrived() + "",
                    data.get( 0 ).getPhone(),
                    data.get( 0 ).getEmail() + "",
                    data.get( 0 ).getCollege() + "",
                    data.get( 0 ).getEventid() + "",
                    data.get( 0 ).getEventName() + "",
                    data.get( 0 ).getTimestamp() + "",
                    data.get( 0 ).getQrcode() + "",
                    data.get( 0 ).getArrived(),
                    data.get( 0 ).getPaymentstatus(),
                    data.get( 0 ).getTeam() + ""
            );
        }
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private final List<EventDataModel> mData;

        PopulateDbAsync(AppDatabase db, List<EventDataModel> data) {
            mDb = db;
            this.mData = data;

        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithData( mDb, mData );
            return null;

        }
    }

    private static class PopulateTicketDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private final List<TicketModel> mData;

        PopulateTicketDbAsync(AppDatabase db, List<TicketModel> data) {
            mDb = db;
            mData = data;

        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTicketData( mDb, mData );
            return null;

        }
    }

}
