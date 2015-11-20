package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.daimajia.slider.library.SliderLayout;
import com.teamawesome.navii.R;

import org.joda.time.DateTime;

/**
 * Created by williamkim on 15-11-19.
 */
public class TravelParameterFragment extends MainFragment {
    private BootstrapCircleThumbnail mChildUpButton;
    private BootstrapCircleThumbnail mChildDownButton;
    private AwesomeTextView mChildTextView;

    private int childCounter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_travel_parameter, container, false);
        mChildUpButton = (BootstrapCircleThumbnail) v.findViewById(R.id.travel_paramenter_child_up_button);
        mChildDownButton = (BootstrapCircleThumbnail) v.findViewById(R.id.travel_parameter_child_down_button);
        mChildTextView = (AwesomeTextView) v.findViewById(R.id.travel_paramenter_child_text);

        mChildUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++childCounter;
                setChildTextViewText();
            }
        });

        mChildDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (childCounter > 0) {
                    --childCounter;
                    setChildTextViewText();
                }
            }
        });

        return v;
    }

    private void setChildTextViewText() {
        mChildTextView.setText(Integer.toString(childCounter) + " children");
    }
}
