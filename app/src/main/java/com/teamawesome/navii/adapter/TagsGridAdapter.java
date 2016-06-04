package com.teamawesome.navii.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teamawesome.navii.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjung on 02/06/16.
 */
public class TagsGridAdapter extends ArrayAdapter<String> {

    private List<String> mTags;
    private List<String> mSelectedTags = new ArrayList<>();
    SparseBooleanArray mSelectedPositions = new SparseBooleanArray();


    public TagsGridAdapter(Context context, int resource, List<String> tags) {
        super(context, resource, tags);
        this.mTags = tags;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.tags_selectable_grid_item, null);
        } else {
            view = convertView;
        }

        ((TextView) view).setText(mTags.get(position));

        if (mSelectedPositions.get(position)) {
            Log.d("Selected TAG", mTags.get(position));
            view.setSelected(true);
            view.setBackground(getContext().getResources().getDrawable(R.drawable
                    .tag_grid_item_selected_background, null));

        } else {
            Log.d("Not TAG", mTags.get(position));
            view.setSelected(false);
            view.setBackground(getContext().getResources().getDrawable(R.drawable
                    .tag_grid_item_background, null));
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = v.isSelected();
                v.setSelected(!selected);
                if (v.isSelected()) {
                    v.setBackground(getContext().getResources().getDrawable(R.drawable
                            .tag_grid_item_selected_background, null));
                } else {
                    v.setBackground(getContext().getResources().getDrawable(R.drawable
                            .tag_grid_item_background, null));
                }
                mSelectedPositions.put(position, !selected);
            }
        });

        return view;
    }

    public List<String> getSelectedTags() {
        return mSelectedTags;
    }
}
