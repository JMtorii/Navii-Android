package com.teamawesome.navii.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.teamawesome.navii.R;
import com.teamawesome.navii.fragment.intro.PreferencesFragment;
import com.teamawesome.navii.server.model.Preference;
import com.teamawesome.navii.util.AnalyticsManager;
import com.teamawesome.navii.util.RestClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sjung on 19/06/16.
 */
public class PreferencesActivity extends AppCompatActivity {
    private Adapter mAdapter;

    @BindView(R.id.itinerary_schedule_viewpager)
    ViewPager mViewPager;

    private List<List<String>> prefetchedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        ButterKnife.bind(this);
        Observable<List<Preference>> preferencesObservable = RestClient.userPreferenceAPI.getUsersPreferences();
        final Context context = this;
        prefetchedPreferences = new ArrayList<>();
        preferencesObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Preference>>() {
                    @Override
                    public void onCompleted() {
                        //Nothing to do
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("failed", e.getMessage());

                        Toast.makeText(context, "Cannot get preferences", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<Preference> preferences) {
                        int currentType = 1;
                        List<String> current = new ArrayList<>();
                        for (int i = 0; i < preferences.size(); i++) {
                            Preference preference = preferences.get(i);
                            if (currentType != preference.getPreferenceType()) {
                                prefetchedPreferences.add(current);
                                currentType = preference.getPreferenceType();
                                current = new ArrayList<>();
                            }
                            current.add(preference.getPreference());
                        }
                        prefetchedPreferences.add(current);
                        setupViewPager(mViewPager);
                    }
                });

        AnalyticsManager.getMixpanel().track("PreferencesActivity - onCreate");
    }

    @OnClick(R.id.preferences_next_button)
    public void onClick() {
        int index = mViewPager.getCurrentItem();
        int maxIndex = mViewPager.getChildCount();
        //Currently the travel participants fragment precedes the budget fragment
        //Makes the next button invisible if the "next" fragment is budget
        final Activity activity = this;
        if (index < maxIndex) {
            mViewPager.setCurrentItem(index + 1, true);
        } else {
            List<Preference> preferences = new ArrayList<>();
            for (int i = 0; i < mAdapter.getCount(); i++) {
                PreferencesFragment preferencesFragment = (PreferencesFragment) mAdapter.getItem(i);
                preferences.addAll(preferencesFragment.getSelectedPreferences());
            }

            Call<Void> deleteCall = RestClient.userPreferenceAPI.deleteAllUserPreference();
            final Call<Void> createCall = RestClient.userPreferenceAPI.createUserPreference(preferences);

            // enqueues the delete call to delete the existing preferences for the user to
            // replace with new ones
            deleteCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    Log.d("Delete: code", String.valueOf(response.code()));
                    createCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Response<Void> response, Retrofit retrofit) {
                            Log.i("call response: code", String.valueOf(response.code()));
                            //Switch to the next preference type  (change screens)
                            if (response.code() == 201) {
                                switchToThankYouActivity();
                            } else {
                                Toast.makeText(activity, "Could not save preferences", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.i("failed", t.getMessage());
                        }
                    });
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.i("failed", t.getMessage());
                }
            });

            // enqueues the create call to create the selected preferences for the user
        }
    }

    public List<String> getPrefetchedPreferences(int preferenceType) {
        return prefetchedPreferences.get(preferenceType);
    }

    private void switchToThankYouActivity() {
        boolean fromSignup = getIntent().getBooleanExtra("from_signup", false);

        if (fromSignup) {
            Intent thankYouIntent = new Intent(this, ThankYouActivity.class);
            startActivity(thankYouIntent);
        }
        finish();
    }

    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new Adapter(getSupportFragmentManager());
        mAdapter.addFragment(PreferencesFragment.newInstance(1), "1");
        mAdapter.addFragment(PreferencesFragment.newInstance(2), "2");
        mAdapter.addFragment(PreferencesFragment.newInstance(3), "3");
        viewPager.setAdapter(mAdapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
