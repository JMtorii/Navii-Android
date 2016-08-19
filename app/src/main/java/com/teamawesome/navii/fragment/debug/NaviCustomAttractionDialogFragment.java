package com.teamawesome.navii.fragment.debug;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.Attraction;
import com.teamawesome.navii.server.model.Location;
import com.teamawesome.navii.views.MainLatoButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ian on 2016-08-17.
 */
public class NaviCustomAttractionDialogFragment extends DialogFragment{

    public interface NoticeDialogListener {

        public void onDialogPositiveClick(int day, Attraction attraction);
        public void onDialogNegativeClick(DialogFragment dialog);

    }

    public static int days;

    NoticeDialogListener mListener;

    @BindView(R.id.custom_attraction_day_picker)
    public NumberPicker mAttractionDay;

    @BindView(R.id.create_custom_attraction)
    public MainLatoButton mCreateCustomAttractionButton;

    @BindView(R.id.custom_attraction_name)
    public TextInputEditText mCustomAttractionName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.dialog_custom_attraction, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException c){
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        mAttractionDay.setMinValue(1);
        mAttractionDay.setMaxValue(days);
        mAttractionDay.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        this.getDialog().setCanceledOnTouchOutside(true);
        this.getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @OnClick(R.id.create_custom_attraction)
    public void createCustomAttraction(){
        Location customLocation = new Location.Builder().address("123 Sesame ST.").build();
        Attraction custom = new Attraction.Builder()
                .name(mCustomAttractionName.getText().toString())
                .description("User Created")
                .location(customLocation)
                .photoUri("http://cpl.jumpfactor.netdna-cdn.com/wp-content/uploads/2015/04/plumber-Toronto-Toronto-plumbers.jpg")
                .build();
        mListener.onDialogPositiveClick(mAttractionDay.getValue(),custom);
        this.dismiss();
    }
}
