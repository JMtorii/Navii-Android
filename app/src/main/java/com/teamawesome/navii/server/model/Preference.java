package com.teamawesome.navii.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sjung on 05/11/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Preference {

    @JsonProperty(value = "preference")
    private String preference;

    @JsonProperty(value = "counter")
    private int counter;

    @JsonProperty(value = "preference_type")
    private int preferenceType;

    @JsonProperty(value = "photoURL")
    private String photoUrl;

    public String getPreference() {
        return preference;
    }

    public int getPreferenceType() {
        return preferenceType;
    }

    public int getCounter() {
        return counter;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Preference() {}

    private Preference(Builder builder) {
        this.preference = builder.preference;
        this.counter = builder.counter;
        this.preferenceType = builder.preferenceType;
        this.photoUrl = builder.photoUrl;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {

        private String preference;
        private int counter;
        private int preferenceType;
        private String photoUrl;

        public Builder() {
        }

        public Builder preference(String preference) {
            this.preference = preference;
            return this;
        }

        public Builder counter(int counter) {
            this.counter = counter;
            return this;
        }

        public Builder preferenceType(int preferenceType) {
            this.preferenceType = preferenceType;
            return this;
        }

        public Builder photoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
            return this;
        }

        public Preference build() {
            return new Preference(this);
        }
    }
}