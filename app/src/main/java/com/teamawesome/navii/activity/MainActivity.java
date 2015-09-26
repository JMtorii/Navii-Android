package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.debug.TestFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // If you call this, you MUST commit afterwards. Not doing so will cause a stack error
    public FragmentTransaction getFragmentTransaction() {
        return getSupportFragmentManager().beginTransaction();
    }
}
