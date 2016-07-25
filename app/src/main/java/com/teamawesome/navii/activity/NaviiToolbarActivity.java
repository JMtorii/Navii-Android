package com.teamawesome.navii.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.teamawesome.navii.R;
import com.teamawesome.navii.util.ToolbarConfiguration;
import com.teamawesome.navii.views.MainLatoButton;
import com.teamawesome.navii.views.MainLatoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JMtorii on 16-06-19.
 */
public abstract class NaviiToolbarActivity extends AppCompatActivity {
    public abstract ToolbarConfiguration getToolbarConfiguration();

    public abstract void onLeftButtonClick();

    public abstract void onRightButtonClick();

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @Nullable
    @BindView(R.id.tv_toolbar_title)
    public MainLatoTextView titleText;

    @Nullable
    @BindView(R.id.imgbtn_toolbar_left)
    public ImageButton leftImageButton;

    @Nullable
    @BindView(R.id.imgbtn_toolbar_right)
    public ImageButton rightImageButton;

    @Nullable
    @BindView(R.id.btn_toolbar_left)
    public MainLatoButton leftTextButton;

    @Nullable
    @BindView(R.id.btn_toolbar_right)
    public MainLatoButton rightTextButton;

    // TODO: implement loading screen
//    @Nullable
//    @BindView(R.id.loading_view)
//    public View loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getToolbarConfiguration().getLayoutResId());
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            if (titleText != null && getToolbarConfiguration().getTitleResId() != 0) {
                titleText.setText(getToolbarConfiguration().getTitleResId());
            }
        }

        if (getToolbarConfiguration().getLeftButtonResType() == ToolbarConfiguration.BUTTON_TYPE_NONE) {
            if (leftImageButton != null) {
                leftImageButton.setVisibility(View.GONE);
            }
            if (leftTextButton != null) {
                leftTextButton.setVisibility(View.GONE);
            }
        } else if (getToolbarConfiguration().isLeftButtonImage()) {
            if (leftImageButton != null) {
                leftImageButton.setVisibility(View.VISIBLE);
                leftImageButton.setImageResource(getToolbarConfiguration().getLeftResId());
                leftImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLeftButtonClick();
                    }
                });
            }

            if (leftTextButton != null) {
                leftTextButton.setVisibility(View.GONE);
            }
        } else {
            if (leftImageButton != null) {
                leftImageButton.setVisibility(View.GONE);
            }

            if (leftTextButton != null) {
                leftTextButton.setVisibility(View.VISIBLE);
                leftTextButton.setText(getToolbarConfiguration().getLeftResId());
                leftTextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLeftButtonClick();
                    }
                });
            }
        }

        if (getToolbarConfiguration().getRightButtonResType() == ToolbarConfiguration.BUTTON_TYPE_NONE) {
            if (rightImageButton != null) {
                rightImageButton.setVisibility(View.GONE);
            }
            if (rightTextButton != null) {
                rightTextButton.setVisibility(View.GONE);
            }
        } else if (getToolbarConfiguration().isRightButtonImage()) {
            if (rightImageButton != null) {
                rightImageButton.setVisibility(View.VISIBLE);
                rightImageButton.setImageResource(getToolbarConfiguration().getRightResId());
                rightImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRightButtonClick();
                    }
                });
            }

            if (rightTextButton != null) {
                rightTextButton.setVisibility(View.GONE);
            }
        } else {
            if (rightImageButton != null) {
                rightImageButton.setVisibility(View.GONE);
            }
            if (rightTextButton != null) {
                rightTextButton.setVisibility(View.VISIBLE);
                rightTextButton.setText(getToolbarConfiguration().getRightResId());
                rightTextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRightButtonClick();
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onModalBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.hold, R.anim.slide_out_down);
    }
}
