package com.teamawesome.navii.fragment.planning;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.teamawesome.navii.R;

import org.joda.time.DateTime;


/**
 * Created by JMtorii on 2015-10-13.
 */

public class ChooseLocationFragment extends Fragment implements CalendarDatePickerDialogFragment.OnDateSetListener {

    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    private Button mDateButton;
    private TextView mDateTextView;

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

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DateTime now = DateTime.now();
                CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = CalendarDatePickerDialogFragment
                        .newInstance(ChooseLocationFragment.this, now.getYear(), now.getMonthOfYear() - 1,
                                now.getDayOfMonth());
                calendarDatePickerDialogFragment.setThemeDark(true);
                calendarDatePickerDialogFragment.show(fm, FRAG_TAG_DATE_PICKER);
            }
        });


        return v;
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        mDateTextView.setText("Year: " + year + "\nMonth: " + monthOfYear + "\nDay: " + dayOfMonth);
    }

    @Override
    public void onResume() {
        // Example of reattaching to the fragment
        super.onResume();
        CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = (CalendarDatePickerDialogFragment) getActivity().getSupportFragmentManager()
                .findFragmentByTag(FRAG_TAG_DATE_PICKER);
        if (calendarDatePickerDialogFragment != null) {
            calendarDatePickerDialogFragment.setOnDateSetListener(this);
        }
    }
}
