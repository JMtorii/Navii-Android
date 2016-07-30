package com.teamawesome.navii.fragment.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamawesome.navii.R;
import com.teamawesome.navii.util.NaviiPreferenceData;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ChangePasswordFragment extends NaviiFragment {

    private EditText mCurrentPasswordField;
    private EditText mNewPasswordField;
    private EditText mRepeatNewPasswordField;
    private Button mChangePasswordButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);

        mCurrentPasswordField = (EditText) v.findViewById(R.id.current_password);
        mCurrentPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mNewPasswordField = (EditText) v.findViewById(R.id.new_password);
        mNewPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mRepeatNewPasswordField = (EditText) v.findViewById(R.id.repeat_new_password);
        mRepeatNewPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mChangePasswordButton = (Button) v.findViewById(R.id.change_password_button);
        mChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordsMatch())
                    changePassword();
                else
                    Toast.makeText(getContext(), "Passwords don't match!", Toast.LENGTH_SHORT)
                            .show();
            }
        });

        return v;
    }

    private boolean isPasswordsMatch() {
        String newPassword = mNewPasswordField.getText().toString();
        String newPasswordRepeat = mRepeatNewPasswordField.getText().toString();

        return newPassword.equals(newPasswordRepeat);
    }

    private void changePassword() {
        String userEmail = NaviiPreferenceData.getLoggedInUserEmail();
        String currentPassword = mCurrentPasswordField.getText().toString();
        String newPassword = mNewPasswordField.getText().toString();

        Observable<Void> observable = parentActivity.userAPIObservable
                .changePassword(userEmail, currentPassword, newPassword);
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        // nothing to do here
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "Wrong Password", Toast.LENGTH_LONG).show();
                        Log.i("ChangePasswordFragment", "Something went wrong: " + e.getMessage());

                    }

                    @Override
                    public void onNext(Void v) {
                        // TODO: FIX THIS
                        Log.i("ChangePasswordFragment", "Changed password");
                        if (parentActivity.getSupportFragmentManager().getBackStackEntryCount() > 0)
                            parentActivity.getSupportFragmentManager().popBackStackImmediate();
                    }
                });
    }
}