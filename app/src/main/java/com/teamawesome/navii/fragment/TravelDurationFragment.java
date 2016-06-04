package com.teamawesome.navii.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.MainActivity;
import com.teamawesome.navii.views.MainLatoEditText;
import com.teamawesome.navii.views.MainLatoTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jtorii on 16-05-17.
 */
public class TravelDurationFragment extends Fragment {

    @BindView(R.id.duration_from_text_view)
    MainLatoTextView mFromTextView;

    @BindView(R.id.duration_to_text_view)
    MainLatoTextView mToTextView;

    @BindView(R.id.duration_from_date)
    MainLatoEditText mFromEditText;

    @BindView(R.id.duration_to_date)
    MainLatoEditText mToEditText;

    private Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_travel_duration, container, false);

        ButterKnife.bind(this, v);

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        mFromEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return v;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mFromEditText.setText(sdf.format(myCalendar.getTime()));
    }
}
