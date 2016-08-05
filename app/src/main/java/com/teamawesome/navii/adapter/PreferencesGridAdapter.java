package com.teamawesome.navii.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.Preference;
import com.teamawesome.navii.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sjung on 10/12/15.
 */
public class PreferencesGridAdapter extends RecyclerView.Adapter<PreferencesGridAdapter.PreferencesViewholder> {
    private List<Preference> mPreferences;

    public int getmPreferencesCount() {
        return mPreferencesCount;
    }

    public List<Preference> getmSelectedPreferences() {
        return mSelectedPreferences;
    }

    public List<Preference> mSelectedPreferences;
    private int mPreferencesCount = 0;

    public PreferencesGridAdapter(List<Preference> preferences) {
        super();
        this.mPreferences = preferences;
        mSelectedPreferences = new ArrayList<>();
    }

    @Override
    public PreferencesGridAdapter.PreferencesViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View prefView = inflater.inflate(R.layout.adapter_preferences, parent, false);
        return new PreferencesViewholder(prefView);
    }

    @Override
    public void onBindViewHolder(PreferencesViewholder holder, int position) {
        holder.prefTextView.setText(mPreferences.get(position).getPreference());
        holder.preferenceButton.setTag(mPreferences.get(position));
    }

    @Override
    public int getItemCount() {
        return mPreferences.size();
    }

    class PreferencesViewholder extends RecyclerView.ViewHolder{
        @BindView(R.id.preferenceTextView)
        TextView prefTextView;

        @BindView(R.id.preferenceCheckView)
        View preferenceButton;

        @OnClick(R.id.preferenceCheckView)
        public void onClick(View view){
            if (!preferenceButton.isSelected()) {
                if (mPreferencesCount == Constants.PREFERENCE_MAX_LIMIT) {
                    return;
                }
                mSelectedPreferences.add((Preference) view.getTag());
                ++mPreferencesCount;
            } else {
                mSelectedPreferences.remove((Preference) view.getTag());
                --mPreferencesCount;
            }
            preferenceButton.setSelected(!preferenceButton.isSelected());
        }

        public PreferencesViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
