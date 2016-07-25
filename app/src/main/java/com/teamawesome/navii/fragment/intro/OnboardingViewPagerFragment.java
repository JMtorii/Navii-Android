package com.teamawesome.navii.fragment.intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.teamawesome.navii.R;
import com.teamawesome.navii.views.MainLatoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JMtorii on 16-07-24.
 */
public class OnboardingViewPagerFragment extends Fragment {
    @BindView(R.id.onboarding_imageview)
    ImageView imageView;

    @BindView(R.id.onboarding_title_textview)
    MainLatoTextView titleTextView;

    private static final String ARG_POSITION = "onboarding_arg_position";
    private static final String IDENTIFIER_PREFIX = "com.teamawesome.navii:drawable/navi_temp_toronto_";
    private int mPosition;

    public static OnboardingViewPagerFragment newInstance(int position) {
        OnboardingViewPagerFragment f = new OnboardingViewPagerFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding_view_pager, container, false);
        ButterKnife.bind(this, view);

        String title = getResources().getStringArray(R.array.onboarding_titles)[mPosition];
        titleTextView.setText(title);

        int resId = getResources().getIdentifier(IDENTIFIER_PREFIX + Integer.toString(mPosition + 1), null, null);
        Picasso.with(getActivity()).load(resId).fit().centerCrop().into(imageView);

        return view;
    }
}
