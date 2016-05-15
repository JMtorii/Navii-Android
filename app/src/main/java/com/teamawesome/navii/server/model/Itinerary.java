package com.teamawesome.navii.server.model;

import java.util.List;

/**
 * Created by JMtorii on 2015-10-21.
 */
public class Itinerary {
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


