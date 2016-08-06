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
    private List<Attraction> attractions;
    private int duration;
    private int price;
    private String authorId;
    private String nickname;

    public Itinerary() {}

    private Itinerary(Builder builder) {
        this.itineraryId = builder.itineraryId;
        this.price = builder.price;
        this.duration = builder.duration;
        this.description = builder.description;
        this.authorId = builder.authorId;
        this.attractions = builder.attractions;
    }

    protected Itinerary(Parcel in) {
        this.itineraryId = in.readInt();
        this.price = in.readInt();
        this.duration = in.readInt();
        this.description = in.readString();
        this.authorId = in.readString();
        this.attractions = in.createTypedArrayList(Attraction.CREATOR);
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
        dest.writeTypedList(attractions);
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

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public static class Builder {
        private int itineraryId;
        private String description;
        private int duration;
        private List<Attraction> attractions;
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

        public Builder attractions(List<Attraction> attractions) {
            this.attractions = attractions;
            return this;
        }
        public Itinerary build() {
            return new Itinerary(this);
        }
    }
}


