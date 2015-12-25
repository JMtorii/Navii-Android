package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.teamawesome.navii.R;

/**
 * Created by williamkim on 15-11-19.
 */
public class TravelParameterFragment extends NaviiFragment {
    private BootstrapCircleThumbnail mChildUpButton;
    private BootstrapCircleThumbnail mChildDownButton;
    private AwesomeTextView mChildTextView;

    private BootstrapCircleThumbnail mAdultUpButton;
    private BootstrapCircleThumbnail mAdultDownButton;
    private AwesomeTextView mAdultTextView;

    private BootstrapCircleThumbnail mCurrencyUpButton;
    private BootstrapCircleThumbnail mCurrencyDownButton;
    private AwesomeTextView mCurrencyTextView;

    private BootstrapCircleThumbnail mAmountUpButton;
    private BootstrapCircleThumbnail mAmountDownButton;
    private AwesomeTextView mAmountTextView;

    private int childCounter = 0;
    private int adultCounter = 0;
    private int currenciesIndex = 0;
    private int amountCounter = 0;

    private String[] currencies = {"CAD", "USD", "EUR", "GBP", "INR", "JPY"};

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

        mAdultUpButton = (BootstrapCircleThumbnail) v.findViewById(R.id.travel_paramenter_adult_up_button);
        mAdultDownButton = (BootstrapCircleThumbnail) v.findViewById(R.id.travel_parameter_adult_down_button);
        mAdultTextView = (AwesomeTextView) v.findViewById(R.id.travel_paramenter_adult_text);

        mCurrencyUpButton = (BootstrapCircleThumbnail) v.findViewById(R.id.travel_paramenter_currency_up_button);
        mCurrencyDownButton = (BootstrapCircleThumbnail) v.findViewById(R.id.travel_parameter_currency_down_button);
        mCurrencyTextView = (AwesomeTextView) v.findViewById(R.id.travel_paramenter_currency_text);

        mAmountUpButton = (BootstrapCircleThumbnail) v.findViewById(R.id.travel_paramenter_amount_up_button);
        mAmountDownButton = (BootstrapCircleThumbnail) v.findViewById(R.id.travel_parameter_amount_down_button);
        mAmountTextView = (AwesomeTextView) v.findViewById(R.id.travel_paramenter_amount_text);

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

        mAdultUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++adultCounter;
                setAdultTextViewText();
            }
        });

        mAdultDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adultCounter > 0) {
                    --adultCounter;
                    setAdultTextViewText();
                }
            }
        });

        mCurrencyUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (currenciesIndex < 5) {
                    ++currenciesIndex;
                    setCurrencyTextViewText();
                }
            }
        });

        mCurrencyDownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (currenciesIndex > 0) {
                    --currenciesIndex;
                    setCurrencyTextViewText();
                }
            }
        });

        mAmountUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ++amountCounter;
                setAmountTextViewText();
            }
        });

        mAmountDownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (amountCounter > 0) {
                    --amountCounter;
                    setAmountTextViewText();
                }
            }
        });

        return v;
    }

    private void setChildTextViewText() {
        mChildTextView.setText(Integer.toString(childCounter) + " children");
    }

    private void setAdultTextViewText() {
        mAdultTextView.setText(Integer.toString(adultCounter) + " adults");
    }

    private void setCurrencyTextViewText() {
        mCurrencyTextView.setText(currencies[currenciesIndex]);
    }

    private void setAmountTextViewText() {
        mAmountTextView.setText(Integer.toString(amountCounter));
    }
}
