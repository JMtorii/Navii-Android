package com.teamawesome.navii.activity.debug;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.IntroActivity;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.server.api.UserAPI;
import com.teamawesome.navii.server.model.User;
import com.teamawesome.navii.util.NaviiMath;

import java.util.Random;

import retrofit.Call;
import retrofit.Callback;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class DebugMainActivity extends ListActivity {
    String[] mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitles = getApplicationContext().getResources().getStringArray(R.array.debug_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                mTitles
        );
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
        if (id == 0) {      // Actual application
            Intent homeIntent = new Intent(this, IntroActivity.class);
            startActivity(homeIntent);
        } else if (id == 1) {       // Skip to main activity
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        } else if (id == 2) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.57.1:8080")    // THIS ONLY WORKS ON JUN'S CASE
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            UserAPI userAPI = retrofit.create(UserAPI.class);

            int randomUniformInt = NaviiMath.randInt(0, 1000);
            User user = new User.Builder()
                    .username("android-user" + String.valueOf(randomUniformInt))
                    .password("android-password" + String.valueOf(randomUniformInt))
                    .salt("android-salt")
//                    .isFacebook(false)
                    .build();
            Call<User> call = userAPI.createUser(user);

            // This does an async call.
            // Use "execute" instead of sync.
            // Call "call.cancel()" to cancel a running request.
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Response<User> response, Retrofit retrofit) {
                    Log.i("response: code", String.valueOf(response.code()));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.i("failed", t.getMessage());
                }
            });
        }
    }
}
