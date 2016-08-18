package com.teamawesome.navii.server.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sjung on 02/07/16.
 *
 * boolean function forces object to be or not be a header function
 * Created for {@link com.teamawesome.navii.adapter.PackageScheduleViewAdapter}
 */
public class PackageScheduleListItem implements Parcelable {

    private int itemType;
    private String name;
    private Attraction attraction;
    private Bitmap bitmap;

    public final static int TYPE_DAY_HEADER = 0;
    public final static int TYPE_MORNING = 1;
    public final static int TYPE_AFTERNOON = 2;
    public final static int TYPE_EVENING = 3;
    public final static int TYPE_ITEM = 4;

    public PackageScheduleListItem() {
    }

    private PackageScheduleListItem(Builder builder) {
        this.itemType = builder.itemType;
        this.attraction = builder.attraction;
        this.name = builder.name;
    }

    public PackageScheduleListItem(Parcel in) {
        itemType = in.readInt();
        name = in.readString();
        attraction = in.readParcelable(Attraction.class.getClassLoader());
    }

    public int getItemType() {
        return itemType;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public String getName() {
        return name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(itemType);
        parcel.writeString(name);
        parcel.writeParcelable(attraction, i);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PackageScheduleListItem> CREATOR = new Creator<PackageScheduleListItem>() {
        @Override
        public PackageScheduleListItem createFromParcel(Parcel in) {
            return new PackageScheduleListItem(in);
        }

        @Override
        public PackageScheduleListItem[] newArray(int size) {
            return new PackageScheduleListItem[size];
        }
    };

    public static class Builder {
        private int itemType;
        private Attraction attraction;
        private String name;

        public Builder itemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder attraction(Attraction attraction) {
            this.attraction = attraction;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public PackageScheduleListItem build() {
            return new PackageScheduleListItem(this);
        }
    }
}

