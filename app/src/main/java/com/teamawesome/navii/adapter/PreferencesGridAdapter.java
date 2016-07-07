package com.teamawesome.navii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teamawesome.navii.R;
import com.teamawesome.navii.server.model.Preference;

import java.util.List;

/**
 * Created by sjung on 10/12/15.
 */
public class PreferencesGridAdapter extends ArrayAdapter<Preference> {
    private List<Preference> mPreferences;


    public PreferencesGridAdapter(Context context, int resource, List<Preference> preferences) {
        super(context, resource, preferences);
        this.mPreferences = preferences;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adapter_preferences, null);
        }

        TextView textView = (TextView) view.findViewById(R.id.preferenceTextView);
        textView.setText(mPreferences.get(position).getPreference());

        view.setTag(mPreferences.get(position));

        return view;
    }
}
