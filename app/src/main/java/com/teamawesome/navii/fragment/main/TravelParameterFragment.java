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

    private View mChildUpButton;
    private View mChildDownButton;
    private AwesomeTextView mChildTextView;

    private View mAdultUpButton;
    private View mAdultDownButton;
    private AwesomeTextView mAdultTextView;

    private View mCurrencyUpButton;
    private View mCurrencyDownButton;
    private AwesomeTextView mCurrencyTextView;

    private View mAmountUpButton;
    private View mAmountDownButton;
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
        View v = inflater.inflate(R.layout.fragment_travel_parameter, container, false);

        mChildUpButton = (View) v.findViewById(R.id.travel_parameter_child_up_button);
        mChildUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++childCounter;
                setChildTextViewText();
            }
        });

        mChildDownButton = (View) v.findViewById(R.id.travel_parameter_child_down_button);
        mChildDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (childCounter > 0) {
                    --childCounter;
                    setChildTextViewText();
                }
            }
        });

        mChildTextView = (AwesomeTextView) v.findViewById(R.id.travel_paramenter_child_text);
        setChildTextViewText();

        mAdultUpButton = (View) v.findViewById(R.id.travel_parameter_adult_up_button);
        mAdultUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++adultCounter;
                setAdultTextViewText();
            }
        });

        mAdultDownButton = (View) v.findViewById(R.id.travel_parameter_adult_down_button);
        mAdultDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adultCounter > 0) {
                    --adultCounter;
                    setAdultTextViewText();
                }
            }
        });

        mAdultTextView = (AwesomeTextView) v.findViewById(R.id.travel_paramenter_adult_text);
        setAdultTextViewText();

        mCurrencyUpButton = (View) v.findViewById(R.id.travel_parameter_currency_up_button);
        mCurrencyUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                currenciesIndex = (currenciesIndex + 1)% 6;
                setCurrencyTextViewText();
            }
        });

        mCurrencyDownButton = (View) v.findViewById(R.id.travel_parameter_currency_down_button);
        mCurrencyDownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                --currenciesIndex;
                if (currenciesIndex < 0) currenciesIndex = currencies.length - 1;
                setCurrencyTextViewText();
            }
        });

        mCurrencyTextView = (AwesomeTextView) v.findViewById(R.id.travel_paramenter_currency_text);
        setCurrencyTextViewText();

        mAmountUpButton = (View) v.findViewById(R.id.travel_parameter_amount_up_button);
        mAmountUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ++amountCounter;
                setAmountTextViewText();
            }
        });

        mAmountDownButton = (View) v.findViewById(R.id.travel_parameter_amount_down_button);
        mAmountDownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (amountCounter > 0) {
                    --amountCounter;
                    setAmountTextViewText();
                }
            }
        });

        mAmountTextView = (AwesomeTextView) v.findViewById(R.id.travel_paramenter_amount_text);
        setAmountTextViewText();

        return v;
    }

    private void setChildTextViewText() {
        if (childCounter > 1)
            mChildTextView.setText(Integer.toString(childCounter) + " children");
        else
            mChildTextView.setText(Integer.toString(childCounter) + " child");
    }

    private void setAdultTextViewText() {
        if (adultCounter > 1)
            mAdultTextView.setText(Integer.toString(adultCounter) + " adults");
        else
            mAdultTextView.setText(Integer.toString(adultCounter) + " adult");
    }

    private void setCurrencyTextViewText() {
        mCurrencyTextView.setText(currencies[currenciesIndex]);
    }

    private void setAmountTextViewText() {
        mAmountTextView.setText(Integer.toString(amountCounter));
    }
}
