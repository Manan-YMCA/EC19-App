package com.elementsculmyca.ec19_app.UI.MyTicketsPage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elementsculmyca.ec19_app.DataSources.LocalServices.AppDatabase;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventLocalModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.EventsDao_Impl;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.UserDao_Impl;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.UserLocalModel;
import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.Util.TicketsGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.ViewHolder> {

    private List<UserLocalModel> ticketsDetails;
    private Context context;
    EventsDao_Impl dao;
    public TicketsAdapter(List<UserLocalModel> ticketsDetails, Context context){
        this.ticketsDetails=ticketsDetails;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_tickets_details,viewGroup,false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        UserLocalModel ticketDetail = ticketsDetails.get(i);
        viewHolder.textViewevent.setText(ticketDetail.getEventName());
        dao=new EventsDao_Impl(AppDatabase.getAppDatabase(context));
         EventLocalModel eventDetails =  dao.getEventByEventId(ticketDetail.getEventid());
        viewHolder.textViewfees.setText(Integer.toString(eventDetails.getFee()));
        viewHolder.textViewvenue.setText(eventDetails.getVenue());
        viewHolder.textViewname.setText(ticketDetail.getName());
        Boolean paymentStatus = ticketDetail.getPaymentstatus();
        if(paymentStatus) {
            viewHolder.textViewstatus.setText("PAID");
            viewHolder.textViewstatus.setTextColor(Color.parseColor("#417505"));
        }else {
            if(eventDetails.getFee()==0){
                viewHolder.textViewstatus.setText("FREE");
                viewHolder.textViewstatus.setTextColor(Color.parseColor("#417505"));
            }else {
                viewHolder.textViewstatus.setText("UNPAID");
                viewHolder.textViewstatus.setTextColor(Color.parseColor("#d0021b"));
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        String timeString= formatter.format(new Date(eventDetails.getStartTime()));
        viewHolder.eventTime.setText(timeString);

        TicketsGenerator ticketsGenerator = new TicketsGenerator();
        Bitmap qrTicket = ticketsGenerator.GenerateClick(ticketDetail.getQrcode(), context, (int) context.getResources().getDimension(R.dimen.ninety), (int) context.getResources().getDimension(R.dimen.ninety), 40, 40);
        viewHolder.qrCode.setImageBitmap(qrTicket);

    }

    @Override
    public int getItemCount() {
        return ticketsDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewevent;
        public TextView textViewfees;
        public TextView textViewvenue;
        public TextView textViewname;
        public TextView textViewstatus;
        public TextView eventTime;
        ImageView qrCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewevent= (TextView) itemView.findViewById(R.id.eventname);
            textViewfees= (TextView) itemView.findViewById(R.id.eventfees);
            textViewvenue=(TextView) itemView.findViewById(R.id.eventvenue);
            textViewname=(TextView) itemView.findViewById(R.id.username);
            textViewstatus=(TextView) itemView.findViewById(R.id.feestatus);
            eventTime = itemView.findViewById(R.id.event_time);
            qrCode = itemView.findViewById(R.id.qrcode);

        }
    }
}
