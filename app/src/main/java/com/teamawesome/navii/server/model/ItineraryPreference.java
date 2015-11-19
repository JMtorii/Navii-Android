package com.teamawesome.navii.server.model;

/**
 * Created by sjung on 10/11/15.
 */
public class ItineraryPreference {

    private int itineraryid;
    private String preference;

    public int getItineraryid() {
        return itineraryid;
    }

    public void setItineraryid(int itineraryid) {
        this.itineraryid = itineraryid;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public ItineraryPreference() {}

    private ItineraryPreference(Builder builder) {

    }

    static Builder getBuilder() {
        return new Builder();
    }

    static class Builder {

        private int itineraryid;
        private String preference;

        private Builder() {}

        Builder itineraryid(int itineraryid) {
            this.itineraryid = itineraryid;
            return this;
        }

        Builder preference(String preference) {
            this.preference = preference;
            return this;
        }

        // TODO: potentially check for non-null values
        ItineraryPreference build() {
            return new ItineraryPreference(this);
        }
    }
}
