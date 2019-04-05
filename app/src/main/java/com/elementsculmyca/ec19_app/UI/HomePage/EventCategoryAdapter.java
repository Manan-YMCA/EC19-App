package com.elementsculmyca.ec19_app.UI.HomePage;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.ClubEventListPage.ClubEventListActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventCategoryAdapter extends RecyclerView.Adapter<EventCategoryAdapter.Viewholder1> {
    public static String TAG="RecyclerVew";

    private ArrayList<ClubEventModel> itemsList;
    private Context mContext;

    public EventCategoryAdapter(Context context, ArrayList<ClubEventModel> itemsList)
    {
        this.itemsList = itemsList;
        this.mContext = context;

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @NonNull
    @Override
    public Viewholder1 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_categories_view,viewGroup,false);
        return new Viewholder1(view);


    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder1 viewholder, int i) {
        final ClubEventModel singleItem = itemsList.get(i);
        String displayName;
        if (singleItem.getDisplayName().length() <= 12) {
            displayName = singleItem.getDisplayName();
        } else {
            displayName = singleItem.getDisplayName().substring(0, 9);
            displayName += "...";
        }
        viewholder.mgenres.setText(displayName);
        viewholder.mimage.setImageResource(R.drawable.drama_x_9_ad_782);
//      if(isNetworkAvailable()) {
        switch (singleItem.getClubName()) {
            case "Manan":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/manan.jpg" )
                        .into( viewholder.mimage );
                break;
            case "Taranuum":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/tarannum.jpg" )
                        .into( viewholder.mimage );
                break;
            case "Ananya":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/ananya.jpg" )
                        .into( viewholder.mimage );
                break;
            case "Jhalak":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/jhalak.jpg" )
                        .into( viewholder.mimage );
                break;
            case "Vividha":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/drama.jpg" )
                        .into( viewholder.mimage );
                break;
            case "IEEE":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/ieee.jpg" )
                        .into( viewholder.mimage );
                break;
            case "SAE":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/automobiles.jpg" )
                        .into( viewholder.mimage );
                break;
            case "Microbird":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/microbird.jpg" )
                        .into( viewholder.mimage );
                break;

            case "Srijan":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/srijan.jpg" )
                        .into( viewholder.mimage );
                break;
            case "Niramayam":
                Picasso.get().load( "https://www.elementsculmyca.com/images/bg/nirmayam2.jpg" )
                        .centerCrop()
                        .fit()
                        .into( viewholder.mimage );
                break;
            case "Samarpan":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/samarpan.jpg" )
                        .into( viewholder.mimage );
                break;
            case "Mechnext":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/mechnext.jpg" )
                        .into( viewholder.mimage );
                break;

            case "Vivekanand Manch":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/vivekanand.jpg" )
                        .into( viewholder.mimage );
                break;
            case "Nataraja":
                Picasso.get().load( "https://www.elementsculmyca.com/EC19Website/images/bg/nataraja.jpg" )
                        .into( viewholder.mimage );
                break;

            default:
                viewholder.mimage.setImageResource( R.drawable.drama_x_9_ad_782 );


        }
//      }


        viewholder.mimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ClubEventListActivity.class)
                        .putExtra("clubname", singleItem.getClubName())
                        .putExtra("clubdisplay", singleItem.getDisplayName()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    public class Viewholder1 extends RecyclerView.ViewHolder{
           private ImageView mimage;
           private TextView mgenres;

          public  Viewholder1(View itemView)
            {
                super(itemView);
                mimage=(ImageView) itemView.findViewById(R.id.Categories_image);
                mgenres=(TextView) itemView.findViewById(R.id.categories_genre);
                }
        }


}
