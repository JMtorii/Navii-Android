package com.teamawesome.navii.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamawesome.navii.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sjung on 02/06/16.
 */
public class TagGridAdapter extends RecyclerView.Adapter<TagGridAdapter.TagGridViewHolder> {

    private final Context mContext;
    private List<String> mTags;
    private Set<String> mSelectedTags;

    public TagGridAdapter(Context context, List<String> tags) {
        super();
        this.mContext = context;
        this.mTags = tags;
        this.mSelectedTags = new HashSet<>();
    }

    @Override
    public TagGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tags_selectable_grid_item, null);
        return new TagGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TagGridViewHolder holder, int position) {
        String tag = mTags.get(position);
        holder.name.setText(tag);
        holder.name.setTag(tag);
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public List<String> getSelectedTags() {
        return new ArrayList<>(mSelectedTags);
    }

    class TagGridViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tag_name)
        TextView name;

        @OnClick(R.id.tag_name)
        void tagSelect(View v) {
            boolean selected = v.isSelected();
            v.setSelected(!selected);
            if (selected) {
                mSelectedTags.add(v.getTag().toString());
            } else {
                mSelectedTags.remove(v.getTag().toString());
            }
        }

        public TagGridViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
