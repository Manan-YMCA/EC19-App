package com.elementsculmyca.ec19_app.UI.DeveloperPage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elementsculmyca.ec19_app.R;

import java.util.ArrayList;

public class DeveloperFragment extends Fragment {
    private ArrayList<DeveloperModel> developers = new ArrayList<>();
    RecyclerView recyclerView;
    DevloperAdapter mAdapter;
    DeveloperModel developer;
    Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    @NonNull
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_developers, container, false);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        developer = new DeveloperModel( "https://i.ibb.co/kX7yn2k/pp.jpg", "Prerna Suneja", "Team Head", "https://github.com/Prerna1", "https://www.linkedin.com/in/prerna-suneja-96b97714b/" );
        developers.add(developer);
        developer = new DeveloperModel( "https://www.elementsculmyca.com/EC19Website/images/team/dev/shubham.jpeg", "Team Head", "Godfather", "https://github.com/shubham0008", "https://www.linkedin.com/in/shubham0008/" );
        developers.add(developer);
        developer = new DeveloperModel( "https://i.ibb.co/QFJQBqw/20190404-151234.jpg", "Jayati", "Team Head", "https://github.com/jayati2016", "https://www.linkedin.com/mwlite/me" );
        developers.add(developer);
        developer = new DeveloperModel( "https://www.elementsculmyca.com/EC19Website/images/team/dev/shubham.jpeg", "Prince Batra", "Backend Developer", "https://github.com/shubham0008", "https://www.linkedin.com/in/shubham0008/" );
        developers.add(developer);
        developer = new DeveloperModel( "https://www.elementsculmyca.com/EC19Website/images/team/dev/shubham.jpeg", "Manmeet", "Backend Developer", "https://github.com/shubham0008", "https://www.linkedin.com/in/shubham0008/" );
        developers.add(developer);
        developer = new DeveloperModel( "https://i.ibb.co/nDTMmpt/IMG-20190307-180455-596.jpg", "Rishabh Mahajan", "App Developer", "www.github.com/rishabhrishabh", "https://www.linkedin.com/in/rishabh-mahajan-a12764151" );
        developers.add(developer);
        developer = new DeveloperModel( "https://i.ibb.co/Zz8bbVW/IMG-20190404-133650.jpg", "Priyanka", "App Developer", "https://github.com/priyanka1698", "https://www.linkedin.com/in/priyanka-garg-9b386215b" );
        developers.add(developer);
        developer = new DeveloperModel( "https://i.ibb.co/1qt9pcN/IMG-20190404-124641.jpg", "Milind", "App Developer", "https://github.com/milindbishnoi", "https://www.linkedin.com/in/milind-bishnoi-7b7446182" );
        developers.add(developer);
        developer = new DeveloperModel( "https://i.ibb.co/h2tyY4n/IMG-20190404-131254.jpg", "Rahul", "App Developer", "https://github.com/rahulydav", "https://www.linkedin.com/in/rahul-yadav-b22a32150" );
        developers.add(developer);
        developer = new DeveloperModel( "https://i.ibb.co/FKb67gk/dummy.jpg", "Naman", "App Developer", "https://github.com/njnaman", "https://www.linkedin.com/" );
        developers.add(developer);
        developer = new DeveloperModel( "https://i.ibb.co/R0rYWbx/Whats-App-Image-2019-04-04-at-5-47-12-PM.jpg", "Sayantanu", "App Developer", "https://github.com/sayantanu-dey", "https://www.linkedin.com/in/sayantanu-dey-434b93168/" );
        developers.add(developer);
        developer = new DeveloperModel( "https://i.ibb.co/sHwKjdW/2018-11-25-21-33-08-334.jpg", "Vishal", "App Developer", "https://github.com/vishalymca", "https://www.linkedin.com/in/vishal-garg-b09628157" );
        developers.add(developer);
        developer = new DeveloperModel( "https://i.ibb.co/RbHVYPw/Whats-App-Image-2019-04-04-at-2-57-47-PM.jpg", "Yash", "App Developer", "https://github.com/yashdhingra0", "https://www.linkedin.com/in/yash-dhingra-5ab39a155" );
        developers.add(developer);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new DevloperAdapter(developers, mContext);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    private void addData() {


    }
}
