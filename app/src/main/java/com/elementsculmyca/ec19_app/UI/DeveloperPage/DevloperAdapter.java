package com.elementsculmyca.ec19_app.UI.DeveloperPage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import com.elementsculmyca.ec19_app.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class DevloperAdapter extends RecyclerView.Adapter<DevloperAdapter.MyViewholder> {
    public static String TAG="RecyclerVew";

    private ArrayList<DeveloperModel> itemsList;
    private Context mContext;

    public DevloperAdapter( ArrayList<DeveloperModel> itemsList,Context context)
    {
        this.itemsList = itemsList;
        this.mContext = context;

    }

    @NonNull
    @Override
    public DevloperAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_developers,viewGroup,false);
        return new MyViewholder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder viewholder, int i) {

        Picasso.get().load(itemsList.get(i).getImageUri()).into(viewholder.mimage);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    public class MyViewholder extends RecyclerView.ViewHolder{
        private ImageView mimage;
        private TextView mgenres;
        private ImageView linkd, github;
        public  MyViewholder(View itemView)
        {
            super(itemView);
            mimage = itemView.findViewById(R.id.back);
            linkd = itemView.findViewById(R.id.linkedin);
            github = itemView.findViewById(R.id.github);

        }
    }

}
