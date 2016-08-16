package com.teamawesome.navii.server.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by JMtorii on 2015-10-21.
 */
public class Itinerary implements Parcelable{
    private int itineraryId;
    private String description;
    private List<PackageScheduleListItem> packageScheduleListItems;
    private int duration;
    private int price;
    private String authorId;

    public Itinerary() {}

    private Itinerary(Builder builder) {
        this.itineraryId = builder.itineraryId;
        this.price = builder.price;
        this.duration = builder.duration;
        this.description = builder.description;
        this.authorId = builder.authorId;
        this.packageScheduleListItems = builder.packageScheduleListItems;
    }

    protected Itinerary(Parcel in) {
        this.itineraryId = in.readInt();
        this.price = in.readInt();
        this.duration = in.readInt();
        this.description = in.readString();
        this.authorId = in.readString();
        this.packageScheduleListItems = in.createTypedArrayList(PackageScheduleListItem.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(itineraryId);
        dest.writeInt(price);
        dest.writeInt(duration);
        dest.writeString(description);
        dest.writeString(authorId);
        dest.writeTypedList(packageScheduleListItems);
    }

    public static final Creator<Itinerary> CREATOR = new Creator<Itinerary>() {
        @Override
        public Itinerary createFromParcel(Parcel in) {
            return new Itinerary(in);
        }

        @Override
        public Itinerary[] newArray(int size) {
            return new Itinerary[size];
        }
    };

    public int getItineraryId() {
        return itineraryId;
    }

    public int getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getDescription() {
        return description;
    }

    public List<PackageScheduleListItem> getPackageScheduleListItems() {
        return packageScheduleListItems;
    }

    public static class Builder {
        private int itineraryId;
        private String description;
        private int duration;
        private List<PackageScheduleListItem> packageScheduleListItems;
        private int price;
        private String authorId;

        public Builder() {}

        public Builder itineraryId(int itineraryId) {
            this.itineraryId = itineraryId;
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder authorId(String authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder packageScheduleListItems(List<PackageScheduleListItem> packageScheduleListItems) {
            this.packageScheduleListItems = packageScheduleListItems;
            return this;
        }

        public Itinerary build() {
            return new Itinerary(this);
        }
    }
}


