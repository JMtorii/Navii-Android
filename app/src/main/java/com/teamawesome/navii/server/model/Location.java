package com.teamawesome.navii.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sjung on 09/05/16.
 */
public class Location {
    @JsonProperty(value = "country_code")
    private String countryCode;
    private String neighborhoods;
    private String address;
    private double latitude;
    private double longitude;
    private String city;

    public Location() {
    }

    private Location(Builder builder) {
        this.countryCode = builder.countryCode;
        this.neighborhoods = builder.neighborhoods;
        this.address = builder.address;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.city = builder.city;
    }


    public String getCountry_code() {
        return countryCode;
    }

    public String getNeighborhoods() {
        return neighborhoods;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public static class Builder {
        private String countryCode;
        private String neighborhoods;
        private String address;
        private double latitude;
        private double longitude;
        private String city;

        public Builder() {
        }

        public Builder countryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }


        public Builder neighborhoods(String neighborhoods) {
            this.neighborhoods = neighborhoods;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Location build() {
            return new Location(this);
        }

    }
}
