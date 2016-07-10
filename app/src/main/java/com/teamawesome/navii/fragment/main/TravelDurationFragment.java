package com.teamawesome.navii.fragment.main;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.teamawesome.navii.R;
import com.teamawesome.navii.views.MainLatoTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jtorii on 16-05-17.
 */
public class TravelDurationFragment extends NaviiParallaxFragment {

    @BindView(R.id.duration_from_text_view)
    MainLatoTextView mFromTextView;

    @BindView(R.id.duration_to_text_view)
    MainLatoTextView mToTextView;

    @BindView(R.id.duration_from_date)
    MainLatoTextView mFromDateTextView;

    @BindView(R.id.duration_to_date)
    MainLatoTextView mToDateTextView;

    private Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;

    private Calendar myCalendar2 = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_travel_duration, container, false);

        ButterKnife.bind(this, v);

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Calendar suggestedCalendar = Calendar.getInstance();
                suggestedCalendar.set(Calendar.YEAR, year);
                suggestedCalendar.set(Calendar.MONTH, monthOfYear);
                suggestedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                if (Calendar.getInstance().compareTo(suggestedCalendar) <= 0) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                } else {
                    Toast.makeText(getActivity(),
                            "The starting date cannot be set before today.",
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Calendar suggestedCalendar = Calendar.getInstance();
                suggestedCalendar.set(Calendar.YEAR, year);
                suggestedCalendar.set(Calendar.MONTH, monthOfYear);
                suggestedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                if (myCalendar.compareTo(suggestedCalendar) < 0) {
                    myCalendar2.set(Calendar.YEAR, year);
                    myCalendar2.set(Calendar.MONTH, monthOfYear);
                    myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel2();
                } else {
                    Toast.makeText(getActivity(),
                            "The ending date must come after the starting date.",
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        mFromDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mToDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), R.style.DialogTheme, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        myCalendar2.add(Calendar.DAY_OF_YEAR, 1);

        updateLabel();
        updateLabel2();

        return v;
    }

    private void updateLabel() {
        String myFormat = "MMMM d, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mFromDateTextView.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "MMMM d, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mToDateTextView.setText(sdf.format(myCalendar2.getTime()));
    }
}
