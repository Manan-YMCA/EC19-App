package com.elementsculmyca.ec19_app.UI.ClubEventListPage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elementsculmyca.ec19_app.DataSources.DataModels.EventDataModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.AppDatabase;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.UserDao;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.UserDao_Impl;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.UserLocalModel;
import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.EventPage.SingleEventActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> implements Filterable {
    private ArrayList<EventDataModel> eventList;
    private Context context;

    private ArrayList<EventDataModel> eventListcopy;

    public EventAdapter(ArrayList<EventDataModel> events,Context context)
    {
        this.eventList = events;
        this.context = context;
        eventListcopy=new ArrayList<EventDataModel>(eventList);
    }
    public EventAdapter()
    {

    }

    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_event_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventAdapter.ViewHolder viewHolder, int i) {
        final EventDataModel event = eventList.get(i);
        UserDao_Impl daoUser;
        daoUser=new UserDao_Impl(AppDatabase.getAppDatabase(context));
        UserLocalModel user = daoUser.getTicketbyId(event.getId());
        if(user==null) {
            viewHolder.registerButton.setText("Register Now!");
        }
        else
            viewHolder.registerButton.setText("View Ticket");
        viewHolder.eventName.setText(event.getTitle().substring(0, Math.min(event.getTitle().length(), 15)));
        if (event.getTitle().length() > 15)
            viewHolder.eventName.append("...");
        if(event.getEventType().equals("team")) {
            viewHolder.eventTypeLayout.setVisibility(View.VISIBLE);
            viewHolder.eventType.setText("Team Event");
            viewHolder.eventTypeImage.setBackgroundResource(R.drawable.ic_people_black_24dp);
        }
        else if(event.getEventType().equals("solo")) {
            viewHolder.eventTypeLayout.setVisibility(View.VISIBLE);
            viewHolder.eventType.setText("Solo Event");
            viewHolder.eventTypeImage.setBackgroundResource(R.drawable.ic_person_black_24dp);
        }
        else{
            viewHolder.eventTypeLayout.setVisibility(View.GONE);
        }
        String description;
        viewHolder.eventDescription.setText(event.getDesc().substring(0, Math.min(event.getDesc().length(), 150)));
        if (event.getDesc().length() > 150)
            viewHolder.eventDescription.append("...");
        viewHolder.eventVenue.setText(event.getVenue().substring(0, Math.min(event.getVenue().length(), 15)));
        if (event.getVenue().length() > 15)
            viewHolder.eventVenue.append("...");
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        String timeString= formatter.format(new Date(event.getTime().getFrom()));
        viewHolder.eventTime.setText(timeString);
        viewHolder.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context , SingleEventActivity.class)
                        .putExtra("eventId", event.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;

    }

    private Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<EventDataModel> filteredList=new ArrayList<>();
            if(charSequence==null ||charSequence.length()==0 )
            {
                filteredList.addAll(eventListcopy);
            }
            else
            {
                String filterpattern=charSequence.toString().toLowerCase().trim();
                for (EventDataModel item:eventListcopy)
                {
                    if(item.getTitle().toLowerCase().contains(filterpattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            eventList.clear();
            eventList.addAll((List)filterResults.values);
            notifyDataSetChanged();


        }
    };




    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView eventName, eventDescription, eventTime,eventVenue,eventType,registerButton;
        ImageView eventTypeImage;
        LinearLayout eventTypeLayout;

        public ViewHolder(View view) {
            super(view);

            eventName = (TextView) view.findViewById(R.id.event_name);
            eventType = view.findViewById(R.id.event_type);
            //eventDescription.setTypeface( Typeface.defaultFromStyle(R.font.overpass_black ));
            eventDescription = (TextView) view.findViewById(R.id.tv_event_description);
            eventTime = (TextView) view.findViewById(R.id.event_time);
            eventVenue=(TextView)view.findViewById(R.id.event_venue);
            registerButton=view.findViewById(R.id.register);
            eventTypeImage = view.findViewById(R.id.img_type);
            eventTypeLayout = view.findViewById(R.id.ll_event_type);

        }
    }
}

