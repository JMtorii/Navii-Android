package com.teamawesome.navii.server.model;

import java.util.Date;

/**
 * Created by JMtorii on 2015-10-21.
 */
public class Itinerary {
    private int itineraryId;
    private String description;
    private String tags;
    private Date startDate;
    private Date endDate;
    private int price;
    private int authorId;

    public Itinerary() {}

    private Itinerary(Builder builder) {}

    public int getItineraryId() {
        return itineraryId;
    }

    public int getPrice() {
        return price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getTags() {
        return tags;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getDescription() {
        return description;
    }

    static Builder getBuilder() {
        return new Builder();
    }

    static class Builder {
        private int itineraryId;
        private String description;
        private String tags;
        private Date startDate;
        private Date endDate;
        private int price;
        private int authorId;

        private Builder() {}

        Builder itineraryId(int itineraryId) {
            this.itineraryId = itineraryId;
            return this;
        }

        Builder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        Builder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        Builder description(String description) {
            this.description = description;
            return this;
        }

        Builder authorId(int authorId) {
            this.authorId = authorId;
            return this;
        }

        Builder tags(String tags) {
            this.tags = tags;
            return this;
        }

        Builder price(int price) {
            this.price = price;
            return this;
        }

        Itinerary build() {
            return new Itinerary(this);
        }
    }
}
