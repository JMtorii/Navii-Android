package com.teamawesome.navii.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.debug.TestFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState == null) {
                FragmentTransaction ft = getFragmentTransaction();
                ft.add(R.id.fragment_container, new TestFragment());
                ft.commit();
            }
        }

        setTitle("CHOOSE YOUR CHARACTER!!!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // If you call this, you MUST commit afterwards. Not doing so will cause a stack error
    public FragmentTransaction getFragmentTransaction() {
        return getFragmentManager().beginTransaction();
    }
}
