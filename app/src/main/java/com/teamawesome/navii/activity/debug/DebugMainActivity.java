 package com.teamawesome.navii.activity.debug;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.HeartAndSoulDetailsActivity;
import com.teamawesome.navii.activity.UploadImageTestActivity;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.activity.PackageOverviewActivity;
import com.teamawesome.navii.activity.PreferencesActivity;
import com.teamawesome.navii.server.api.UserAPI;
import com.teamawesome.navii.server.model.User;
import com.teamawesome.navii.util.Constants;
import com.teamawesome.navii.util.NaviiMath;
import com.teamawesome.navii.util.NaviiPreferenceData;

import retrofit.Call;
import retrofit.Callback;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

// TODO: change this to AppCompatActivity for more flexibility
public class DebugMainActivity extends ListActivity {
    String[] mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // change this to the real server address
        NaviiPreferenceData.setIPAddress(Constants.SERVER_URL);
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

        // TODO: make this an enum
        if (id == 0) {              // Actual application
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        } else if (id == 1) {       // Create user
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.SERVER_URL)    // THIS ONLY WORKS IN JUN'S CASE
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            UserAPI userAPI = retrofit.create(UserAPI.class);

            int randomUniformInt = NaviiMath.randInt(0, 1000);
            User user = new User.Builder()
                    .username("android-user" + String.valueOf(randomUniformInt))
                    .password("android-password" + String.valueOf(randomUniformInt))
                    .salt("android-salt")
                    .build();
            Call<Void> call = userAPI.createUser(user);

            // This does an async call.
            // Use "execute" instead for a sync call.
            // Call "call.cancel()" to cancel a running request.
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    Log.i("response: code", String.valueOf(response.code()));
                    Log.i("response: value", String.valueOf(response.body()));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.i("failed", t.getMessage());
                }
            });
        } else if (id == 2) {       // Set ip address
            NaviiPreferenceData.setIPAddress(Constants.SERVER_URL_JUN);
        } else if (id == 3) {       // Package Overview Activity
            Intent packageOverviewIntent = new Intent(this, PackageOverviewActivity.class);
            startActivity(packageOverviewIntent);
        } else if (id == 4) {       // Heart and Soul Details Activity
            Intent heartAndSoulDetailsActivity = new Intent(this, HeartAndSoulDetailsActivity.class);
            startActivity(heartAndSoulDetailsActivity);
        } else if (id == 5) {       // Upload profile image test
            Intent imageUploadTestActivity = new Intent(this, UploadImageTestActivity.class);
            startActivity(imageUploadTestActivity);
        } else if (id == 6) {       // Heart and Soul Details Activity
            Intent preferenceActivity = new Intent(this, PreferencesActivity.class);
            startActivity(preferenceActivity);
        }
    }
}
