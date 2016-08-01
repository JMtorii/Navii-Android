package com.teamawesome.navii.server.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sjung on 09/05/16.
 */
public class Location implements Parcelable {
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


    protected Location(Parcel in) {
        countryCode = in.readString();
        neighborhoods = in.readString();
        address = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        city = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(countryCode);
        dest.writeString(neighborhoods);
        dest.writeString(address);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(city);
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
