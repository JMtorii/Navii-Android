package com.teamawesome.navii.server.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JMtorii on 2015-10-21.
 */
public class Attraction implements Parcelable {
    private int attractionId;
    private String name;
    //    private String location;
    private Location location;
    private String photoUri;
    private String blurbUri;
    private int price;
    private int duration;
    private String purchase;

    public Attraction() {
    }

    private Attraction(Builder builder) {
        this.attractionId = builder.attractionId;
        this.name = builder.name;
        this.location = builder.location;
        this.photoUri = builder.photoUri;
        this.blurbUri = builder.blurbUri;
        this.price = builder.price;
        this.duration = builder.duration;
    }

    protected Attraction(Parcel in) {
        attractionId = in.readInt();
        name = in.readString();
        photoUri = in.readString();
        blurbUri = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        price = in.readInt();
        duration = in.readInt();
        purchase = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(attractionId);
        dest.writeString(name);
        dest.writeString(photoUri);
        dest.writeString(blurbUri);
        dest.writeParcelable(location, PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeInt(price);
        dest.writeInt(duration);
        dest.writeString(purchase);
    }

    public static final Creator<Attraction> CREATOR = new Creator<Attraction>() {
        @Override
        public Attraction createFromParcel(Parcel in) {
            return new Attraction(in);
        }

        @Override
        public Attraction[] newArray(int size) {
            return new Attraction[size];
        }
    };

    public int getAttractionId() {
        return attractionId;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getBlurbUri() {
        return blurbUri;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public int getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public String getPurchase() {
        return purchase;
    }

    public static class Builder {
        private int attractionId;
        private String name;
        private Location location;
        private String photoUri;
        private String blurbUri;
        private int price;
        private int duration;
        private String purchase;

        public Builder() {
        }

        public Builder attractionId(int attractionId) {
            this.attractionId = attractionId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder location(Location location) {
            this.location = location;
            return this;
        }

        public Builder photoUri(String photoUri) {
            this.photoUri = photoUri;
            return this;
        }

        public Builder blurbUri(String blurbUri) {
            this.blurbUri = blurbUri;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder purchase(String purchase) {
            this.purchase = purchase;
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Attraction build() {
            return new Attraction(this);
        }
    }
}