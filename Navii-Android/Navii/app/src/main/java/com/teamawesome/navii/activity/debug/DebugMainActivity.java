package com.teamawesome.navii.activity.debug;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.MainActivity;

public class DebugMainActivity extends ListActivity {

    String[] titleValues = new String[] { "Main Application", "Server", "Login" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, titleValues);
        setListAdapter(adapter);
        setTitle("Debug Main Activity");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();

        // TODO: make this an enum
        if (id == 0) {
            Intent homeIntent = new Intent(this, MainActivity.class);
            startActivity(homeIntent);

        } else if (id == 1) {
//            new PostRequestTask(this).execute();

        } else {

        }
    }
}
