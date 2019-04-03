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

        developer = new DeveloperModel( "https://i.ibb.co/7Gn02Dd/Whats-App-Image-2019-04-04-at-03-52-12.jpg", "Prerna Suneja", "Team Head", "https://github.com/Prerna1", "https://www.linkedin.com/in/prerna-suneja-96b97714b/" );
        developers.add(developer);
        developer = new DeveloperModel( "https://www.elementsculmyca.com/EC19Website/images/team/dev/shubham.jpeg", "Shubham Sharma", "Team Head ka Head", "https://github.com/shubham0008", "https://www.linkedin.com/in/shubham0008/" );
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
