package com.elementsculmyca.ec19_app.UI.aboutPage;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.MenuPage.MenuFragment;

public class AboutActivity extends AppCompatActivity {
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        backButton = findViewById(R.id.iv_back_button);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame, new AboutBaseFragment()).commit();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
