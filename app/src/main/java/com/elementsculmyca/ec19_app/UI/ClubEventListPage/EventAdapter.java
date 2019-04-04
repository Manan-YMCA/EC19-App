package com.elementsculmyca.ec19_app.UI.ClubEventListPage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elementsculmyca.ec19_app.DataSources.DataModels.EventDataModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.AppDatabase;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.UserDao_Impl;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.UserLocalModel;
import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.EventPage.SingleEventActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> implements Filterable {
    private ArrayList<EventDataModel> eventList;
    private Context context;

    private ArrayList<EventDataModel> eventListcopy;

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<EventDataModel> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll( eventListcopy );
            } else {
                String filterpattern = charSequence.toString().toLowerCase().trim();
                for (EventDataModel item : eventListcopy) {
                    if (item.getTitle().toLowerCase().contains( filterpattern )) {
                        filteredList.add( item );
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            eventList.clear();
            eventList.addAll( (List) filterResults.values );
            notifyDataSetChanged();


        }
    };

    public EventAdapter(ArrayList<EventDataModel> events, Context context) {
        this.eventList = events;
        this.context = context;
        eventListcopy = new ArrayList<EventDataModel>( eventList );
    }

    public EventAdapter() {

    }

    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.card_event_list, viewGroup, false );
        return new ViewHolder( view );
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;

    }

    @Override
    public void onBindViewHolder(EventAdapter.ViewHolder viewHolder, int i) {
        final EventDataModel event = eventList.get( i );
        UserDao_Impl daoUser;
        daoUser = new UserDao_Impl( AppDatabase.getAppDatabase( context ) );
        UserLocalModel user = daoUser.getTicketbyId( event.getId() );
        switch (event.getClubname()) {
            case "Manan":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/manan.jpg" )
                        .into( viewHolder.backImg );
                break;
            case "Taranuum":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/tarannum.jpg" )
                        .into( viewHolder.backImg );
                break;
            case "Ananya":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/ananya.jpg" )
                        .into( viewHolder.backImg );
                break;
            case "Jhalak":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/jhalak.jpg" )
                        .into( viewHolder.backImg );
                break;
            case "Vividha":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/drama2.jpg" )
                        .into( viewHolder.backImg );
                break;
            case "IEEE":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/ieee.jpg" )
                        .into( viewHolder.backImg );
                break;
            case "SAE":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/automobiles.jpg" )
                        .into( viewHolder.backImg );
                break;
            case "Microbird":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/microbird.jpg" )
                        .into( viewHolder.backImg );
                break;

            case "Srijan":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/srijan.jpg" )
                        .into( viewHolder.backImg );
                break;
            case "Niramayam":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/jhalak.jpg" )
                        .into( viewHolder.backImg );
                break;
            case "Samarpan":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/samarpan.jpg" )
                        .into( viewHolder.backImg );
                break;
            case "Mechnext":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/mechnext.jpg" )
                        .into( viewHolder.backImg );
                break;

            case "Vivekanand Manch":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/vivekanand.jpg" )
                        .into( viewHolder.backImg );
                break;
            case "Nataraja":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/nataraja.jpg" )
                        .into( viewHolder.backImg );
                break;


        }


        if (user == null) {
            viewHolder.registerButton.setText( "Register Now!" );
        } else
            viewHolder.registerButton.setText( "View Ticket" );
        viewHolder.eventName.setText( event.getTitle().substring( 0, Math.min( event.getTitle().length(), 15 ) ) );
        if (event.getTitle().length() > 15)
            viewHolder.eventName.append( "..." );
        if (event.getEventType().equals( "team" )) {
            viewHolder.eventTypeLayout.setVisibility( View.VISIBLE );
            viewHolder.eventType.setText( "Team Event" );
            viewHolder.eventTypeImage.setBackgroundResource( R.drawable.ic_people_black_24dp );
        } else if (event.getEventType().equals( "solo" )) {
            viewHolder.eventTypeLayout.setVisibility( View.VISIBLE );
            viewHolder.eventType.setText( "Solo Event" );
            viewHolder.eventTypeImage.setBackgroundResource( R.drawable.ic_person_black_24dp );
        } else {
            viewHolder.eventTypeLayout.setVisibility( View.GONE );
        }
        String description;
        viewHolder.eventDescription.setText( event.getDesc().substring( 0, Math.min( event.getDesc().length(), 150 ) ) );
        if (event.getDesc().length() > 150)
            viewHolder.eventDescription.append( "..." );
        viewHolder.eventVenue.setText( event.getVenue().substring( 0, Math.min( event.getVenue().length(), 15 ) ) );
        if (event.getVenue().length() > 15)
            viewHolder.eventVenue.append( "..." );
        SimpleDateFormat formatter = new SimpleDateFormat( "h:mm a" );
        String timeString = formatter.format( new Date( event.getTime().getFrom() ) );
        viewHolder.eventTime.setText( timeString );
        viewHolder.cardEvent.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity( new Intent( context, SingleEventActivity.class )
                        .putExtra( "eventId", event.getId() ) );
            }
        } );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView eventTypeImage, backImg;
        private TextView eventName, eventDescription, eventTime, eventVenue, eventType, registerButton;
        LinearLayout eventTypeLayout;
        RelativeLayout cardEvent;

        public ViewHolder(View view) {
            super( view );

            eventName = (TextView) view.findViewById( R.id.event_name );
            eventType = view.findViewById( R.id.event_type );
            backImg = view.findViewById( R.id.back_imge );
            //eventDescription.setTypeface( Typeface.defaultFromStyle(R.font.overpass_black ));
            eventDescription = (TextView) view.findViewById( R.id.tv_event_description );
            eventTime = (TextView) view.findViewById( R.id.event_time );
            eventVenue = (TextView) view.findViewById( R.id.event_venue );
            registerButton = view.findViewById( R.id.register );
            eventTypeImage = view.findViewById( R.id.img_type );
            eventTypeLayout = view.findViewById( R.id.ll_event_type );
            cardEvent = view.findViewById( R.id.image_event );

        }
    }
}

