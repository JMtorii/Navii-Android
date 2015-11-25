package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.teamawesome.navii.R;
import com.teamawesome.navii.util.Constants;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by JMtorii on 2015-10-13.
 */

public class ChooseLocationFragment extends MainFragment implements CalendarDatePickerDialogFragment.OnDateSetListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    private Button mDateButton;
    private TextView mDateTextView;
    private SliderLayout mSliderLayout;
    private BootstrapButton mNextButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_choose_location, container, false);
        mDateButton = (Button) v.findViewById(R.id.choose_location_date_button);
        mDateTextView = (TextView) v.findViewById(R.id.choose_location_date_textview);
        mSliderLayout = (SliderLayout) v.findViewById(R.id.choose_location_slider_layout);
        mNextButton = (BootstrapButton) v.findViewById(R.id.choose_location_next_button);

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = parentActivity.getSupportFragmentManager();
                DateTime now = DateTime.now();
                CalendarDatePickerDialogFragment calendarDatePickerDialogFragment =
                        CalendarDatePickerDialogFragment.newInstance(
                                ChooseLocationFragment.this, now.getYear(),
                                now.getMonthOfYear() - 1,
                                now.getDayOfMonth()
                        );

                calendarDatePickerDialogFragment.setThemeDark(true);
                calendarDatePickerDialogFragment.show(fm, FRAG_TAG_DATE_PICKER);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TravelParameterFragment fragment = new TravelParameterFragment();

                parentActivity.switchFragment(
                        fragment,
                        Constants.NO_ANIM,
                        Constants.NO_ANIM,
                        Constants.INTRO_PAYMENT_FRAGMENT_TAG,
                        true,
                        true,
                        true
                );
            }
        });

        setupImageSlider();

        return v;
    }

    @Override
    public void onResume() {
        // Example of reattaching to the fragment
        super.onResume();
        CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = (CalendarDatePickerDialogFragment) parentActivity.getSupportFragmentManager()
                .findFragmentByTag(FRAG_TAG_DATE_PICKER);
        if (calendarDatePickerDialogFragment != null) {
            calendarDatePickerDialogFragment.setOnDateSetListener(this);
        }
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mSliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        // TODO: Use String.format() and be careful of \n usage for different screen sizes.
        mDateTextView.setText("Year: " + year + "\nMonth: " + monthOfYear + "\nDay: " + dayOfMonth);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // nothing to do here
    }

    @Override
    public void onPageSelected(int position) {
        // nothing to do here
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // nothing to do here
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        // nothing to do here
    }

    private void setupImageSlider() {
        Map<String,Integer> file_maps = new HashMap<>();
        file_maps.put("Toronto Skyline", R.drawable.toronto1);
        file_maps.put("Paramount Wonderland", R.drawable.toronto2);
        file_maps.put("Toronto Zoo", R.drawable.toronto3);
        file_maps.put("Air Canada Centre", R.drawable.toronto4);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(parentActivity);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name));

            mSliderLayout.addSlider(textSliderView);
        }

        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setDuration(Constants.TRENDING_DESTINATION_DURATION_MS);
        mSliderLayout.addOnPageChangeListener(this);
    }
}
