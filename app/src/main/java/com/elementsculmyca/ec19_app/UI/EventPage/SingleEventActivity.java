package com.elementsculmyca.ec19_app.UI.EventPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elementsculmyca.ec19_app.DataSources.LocalServices.AppDatabase;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventLocalModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventsDao_Impl;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.UserDao_Impl;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.UserLocalModel;
import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.LoginScreen.LoginActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SingleEventActivity extends AppCompatActivity {
    TextView registerButton, eventName, eventDesc, eventVenue, eventDay, eventDate, eventDayTextView, eventFee, eventFeeTextView, category, eventCategory;
    ImageView sharebutton, backButton;
    EventsDao_Impl dao;
    private String eventId;
    RelativeLayout rlBack;
    Boolean deep = false;
    EventLocalModel eventData;
    private String eventClubName, eventCatogery, eventRules, eventPhotoLink, eventCoordinator, eventPrize, eventTags;
    SharedPreferences sharedPreferences;
    String phone;
    UserLocalModel user;
    private long eventStartTime, eventEndTime;
    UserDao_Impl daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_single_event );
        eventName = findViewById( R.id.event_title );
        eventDesc = findViewById( R.id.event_desc );
        eventDay = findViewById( R.id.event_day );
        eventVenue = findViewById( R.id.event_venue );
        registerButton = findViewById( R.id.register );
        sharebutton = findViewById( R.id.share_event );
        eventDate = findViewById( R.id.event_date );
        eventDayTextView = findViewById( R.id.tv_day );
        backButton = findViewById( R.id.back_button );
        eventFee = findViewById( R.id.event_fee );
        category = findViewById( R.id.category );
        eventCategory = findViewById( R.id.event_category );
        eventFeeTextView = findViewById( R.id.tv_fee );
        eventFee = findViewById( R.id.event_fee );
        eventDayTextView.setText( ", Day " );
        rlBack = findViewById( R.id.Image_event );
        sharedPreferences = getSharedPreferences( "login_details", 0 );
        phone = sharedPreferences.getString( "UserPhone", "" );
        dao = new EventsDao_Impl( AppDatabase.getAppDatabase( SingleEventActivity.this ) );
        daoUser = new UserDao_Impl( AppDatabase.getAppDatabase( SingleEventActivity.this ) );
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        if (Intent.ACTION_VIEW.equals( appLinkAction ) && appLinkData != null) {
            deep = true;
            String revStr = new StringBuilder( appLinkData.toString() ).reverse().toString();
            int i;
            for (i = 0; revStr.charAt( i ) != 35; i++) {
            }
            revStr = revStr.substring( 0, i );
            String eventName = new StringBuilder( revStr ).reverse().toString();
            eventName = eventName.replace( "%20", " " );
            eventData = new EventLocalModel();
            eventData = dao.getEventByEventName( eventName );
        } else {
            eventId = getIntent().getStringExtra( "eventId" );
            eventData = new EventLocalModel();
            eventData = dao.getEventByEventId( eventId );
        }
        //add data to page
        setBackgroundImage( eventData );


        eventName.setText( eventData.getTitle() );
        eventId = eventData.getId();
        eventDesc.setText( eventData.getDesc() );
        eventVenue.setText( eventData.getVenue() );
        eventPhotoLink = eventData.getPhotolink();
        eventStartTime = eventData.getStartTime();
        eventCategory.setText( eventData.getCategory() );
        if (eventData.getDay().equals( "1" ) || eventData.getDay().equals( "2" ) || eventData.getDay().equals( "3" ))
            eventDay.setText( eventData.getDay() );
        else
            eventDayTextView.setVisibility( View.GONE );
        if (eventData.getEventType().equals( "team" ))
            category.setText( "Team" );
        else if (eventData.getEventType().equals( "solo" ))
            category.setText( "Solo" );
        else
            category.setVisibility( View.GONE );
        if (eventData.getFee() == 0)
            eventFee.setText( "FREE" );
        else
            eventFee.setText( Integer.toString( eventData.getFee() ) );
        eventStartTime = eventData.getStartTime();
        SimpleDateFormat formatter = new SimpleDateFormat( "dd/MM/yyyy h:mm a" );
        String dateString = formatter.format( new Date( eventStartTime ) );
        eventDate.setText( dateString );
        user = daoUser.getTicketbyId( eventId );
        if (user == null) {
            registerButton.setText( "Register Now!" );
        } else
            registerButton.setText( "View Ticket" );
        final Bundle descFrag = new Bundle();
        descFrag.putString( "eventId", eventId );
        descFrag.putString( "eventName", eventData.getTitle() );
        final DescriptionEventFragment descriptionEventFragment = new DescriptionEventFragment();
        descriptionEventFragment.setArguments( descFrag );
        final RegisterEventFragment registerEventFragment = new RegisterEventFragment();
        registerEventFragment.setArguments( descFrag );

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace( R.id.frame, descriptionEventFragment ).commit();

        backButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deep) {
                    startActivity( new Intent( SingleEventActivity.this, LoginActivity.class ) );
                    finish();
                } else {
                    onBackPressed();
                }
            }
        } );
        registerButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registerButton.getText().equals( "Register Now!" )) {
                    if (phone.equals( "" )) {
                        Toast.makeText( SingleEventActivity.this, "Login to register for events", Toast.LENGTH_SHORT ).show();
                    } else {
                        registerButton.setText( "Show Details" );
                        FragmentManager manager = getSupportFragmentManager();
                        manager.beginTransaction().replace( R.id.frame, registerEventFragment ).commit();
                    }
                } else if (registerButton.getText().equals( "Show Details" )) {
                    user = daoUser.getTicketbyId( eventId );
                    if (user == null) {
                        registerButton.setText( "Register Now!" );
                    } else
                        registerButton.setText( "View Ticket" );
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace( R.id.frame, descriptionEventFragment ).commit();
                } else if (registerButton.getText().equals( "View Ticket" )) {
                    TicketFragment ticketFragment = new TicketFragment();
                    Bundle args = new Bundle();
                    args.putString( "qrcode", user.getQrcode() );
                    ticketFragment.setArguments( args );
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace( R.id.frame, ticketFragment ).commit();
                    registerButton.setText( "Show Details" );
                }
            }
        } );

        sharebutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baseUrl = "http://elementsculmyca.com/events/";
                String parsedUrl = baseUrl + "#" + eventData.getTitle().replaceAll( " ", "%20" );
                String message = "Elements Culmyca 2K19: " + eventData.getTitle() + ". View event by clicking the link: " + parsedUrl;

                Intent intent = new Intent( Intent.ACTION_SEND );
                intent.setType( "text/plain" );
                intent.putExtra( Intent.EXTRA_TEXT, message );
                startActivity( intent );
            }
        } );
    }

    private void setBackgroundImage(EventLocalModel event) {
        switch (event.getClubname()) {
            case "Manan":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/manan.jpg" ).into( new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        rlBack.setBackground( new BitmapDrawable( bitmap ) );
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                } );
                break;
            case "Taranuum":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/tarannum.jpg" )
                        .into( new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                rlBack.setBackground( new BitmapDrawable( bitmap ) );
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        } );
                break;
            case "Ananya":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/ananya.jpg" )
                        .into( new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                rlBack.setBackground( new BitmapDrawable( bitmap ) );
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        } );
                break;
            case "Jhalak":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/jhalak.jpg" )
                        .into( new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                rlBack.setBackground( new BitmapDrawable( bitmap ) );
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        } );
                break;
            case "Vividha":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/drama2.jpg" )
                        .into( new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                rlBack.setBackground( new BitmapDrawable( bitmap ) );
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        } );
                break;
            case "IEEE":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/ieee.jpg" )
                        .into( new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                rlBack.setBackground( new BitmapDrawable( bitmap ) );
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        } );
                break;
            case "SAE":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/automobiles.jpg" )
                        .into( new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                rlBack.setBackground( new BitmapDrawable( bitmap ) );
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        } );
                break;
            case "Microbird":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/microbird.jpg" )
                        .into( new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                rlBack.setBackground( new BitmapDrawable( bitmap ) );
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        } );
                break;

            case "Srijan":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/srijan.jpg" )
                        .into( new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                rlBack.setBackground( new BitmapDrawable( bitmap ) );
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        } );
                break;
            case "Niramayam":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/jhalak.jpg" )
                        .into( new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                rlBack.setBackground( new BitmapDrawable( bitmap ) );
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        } );
                break;
            case "Samarpan":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/samarpan.jpg" )
                        .into( new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                rlBack.setBackground( new BitmapDrawable( bitmap ) );
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        } );
                break;
            case "Mechnext":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/mechnext.jpg" )
                        .into( new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                rlBack.setBackground( new BitmapDrawable( bitmap ) );
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        } );
                break;

            case "Vivekanand Manch":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/vivekanand.jpg" )
                        .into( new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                rlBack.setBackground( new BitmapDrawable( bitmap ) );
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        } );
                break;
            case "Nataraja":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/nataraja.jpg" )
                        .into( new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                rlBack.setBackground( new BitmapDrawable( bitmap ) );
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        } );
                break;


        }
    }

    @Override
    public void onBackPressed() {
        if (deep) {
            startActivity( new Intent( SingleEventActivity.this, LoginActivity.class )
                    .addFlags( Intent.FLAG_ACTIVITY_NEW_TASK ).addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP ) );
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
