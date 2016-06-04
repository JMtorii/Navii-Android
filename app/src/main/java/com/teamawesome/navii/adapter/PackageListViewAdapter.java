package com.teamawesome.navii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamawesome.navii.R;
import com.teamawesome.navii.activity.NaviiActivity;
import com.teamawesome.navii.fragment.main.PackageDescriptionFragment;
import com.teamawesome.navii.server.model.Itinerary;
import com.teamawesome.navii.util.Constants;

import java.util.List;

/**
 * Created by sjung on 10/12/15.
 */
public class PackageListViewAdapter extends ArrayAdapter<Itinerary>{
    private List<Itinerary> itineraries;
    private NaviiActivity parentActivity;

    public PackageListViewAdapter(Context context, int resource, List<Itinerary> itineraries,
                                  NaviiActivity parentActivity) {
        super(context, resource, itineraries);
        this.itineraries = itineraries;
        this.parentActivity = parentActivity;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.itinerary_listitem_layout, null);
        }

        TextView textView = (TextView) view.findViewById(R.id.package_title);
        textView.setText(itineraries.get(position).getDescription());

        view.setTag(itineraries.get(position));

        ImageView imageView = (ImageView) view.findViewById(R.id.package_image_view);

        // TODO: set the background resource
//        Picasso.with(getContext())
//                .load(R.drawable.toronto1)
//                .centerCrop()
//                .fit()
//                .into(imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageDescriptionFragment newInstance = PackageDescriptionFragment
                        .newInstance((Itinerary)v.getTag());
                parentActivity.switchFragment(
                        newInstance,
                        Constants.NO_ANIM,
                        Constants.NO_ANIM,
                        Constants.PACKAGE_DESCRIPTION,
                        true,
                        false,
                        true
                );
            }
        });

        return view;
    }
}
