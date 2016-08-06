package com.teamawesome.navii.server.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by JMtorii on 2015-10-21.
 */
public class Itinerary implements Parcelable{
    private String itineraryNickname;


    private int itineraryId;
    private String description;
    private List<Attraction> attractions;
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
        this.attractions = builder.attractions;
    }

    protected Itinerary(Parcel in) {
        itineraryId = in.readInt();
        description = in.readString();
        attractions = in.createTypedArrayList(Attraction.CREATOR);
        duration = in.readInt();
        price = in.readInt();
        authorId = in.readString();
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

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setItineraryNickname(String itineraryNickname){
        this.itineraryNickname = itineraryNickname;
    }

    public String getItineraryNickname(){
        return this.itineraryNickname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.itineraryId);
        dest.writeString(this.description);
        dest.writeTypedList(this.attractions);
        dest.writeInt(this.duration);
        dest.writeInt(this.price);
        dest.writeString(this.authorId);
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


