package com.teamawesome.navii.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamawesome.navii.R;
import com.teamawesome.navii.views.MainLatoButton;

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

    private List<String> mTags;
    private Set<String> mActiveTags;

    public TagGridAdapter(List<String> tags) {
        super();
        this.mTags = tags;
        this.mActiveTags = new HashSet<>();
    }

    @Override
    public TagGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.tags_selectable_grid_item, null);
        return new TagGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TagGridViewHolder holder, int position) {
        Log.d("Position", ""+position);
        String tag = mTags.get(position);
        String text = tag.substring(0, 1).toUpperCase() + tag.substring(1);
        holder.name.setText(text);
        holder.name.setTag(tag);
        holder.name.setActivated(mActiveTags.contains(tag));
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public List<String> getActiveTags() {
        return new ArrayList<>(mActiveTags);
    }

    class TagGridViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tag_name)
        MainLatoButton name;

        @OnClick(R.id.tag_name)
        void tagSelect(View v) {
            boolean activated = !v.isActivated();

            v.setActivated(activated);
            Log.d("TAG", "Selected: " + activated);

            if (activated) {
                mActiveTags.add(v.getTag().toString());
            } else {
                mActiveTags.remove(v.getTag().toString());
            }
        }

        public TagGridViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
