package com.teamawesome.navii.server.model;

import java.util.List;

/**
 * Created by sjung on 05/08/16.
 */
public class HeartAndSoulPackage {
    private List<List<Itinerary>> itineraries;
    private List<Attraction> extraRestaurants;
    private List<Attraction> extraAttractions;

    public String packageNickname;

    public HeartAndSoulPackage() {
    }

    private HeartAndSoulPackage(Builder builder) {
        itineraries = builder.itineraries;
        extraAttractions = builder.extraAttractions;
        extraRestaurants = builder.extraRestaurants;
    }

    public List<List<Itinerary>>  getItineraries() {
        return itineraries;
    }

    public List<Attraction> getExtraRestaurants() {
        return extraRestaurants;
    }

    public List<Attraction> getExtraAttractions() {
        return extraAttractions;
    }

    public static class Builder {
        private List<List<Itinerary>> itineraries;
        private List<Attraction> extraRestaurants;
        private List<Attraction> extraAttractions;

        public Builder() {
        }

        public Builder itineraries(List<List<Itinerary>> itineraries) {
            this.itineraries = itineraries;
            return this;
        }


        public Builder extraRestaurants(List<Attraction> extraRestaurants) {
            this.extraRestaurants = extraRestaurants;
            return this;
        }

        public Builder extraAttractions(List<Attraction> extraAttractions) {
            this.extraAttractions = extraAttractions;
            return this;
        }

        public HeartAndSoulPackage build() {
            return new HeartAndSoulPackage(this);
        }
    }
}
