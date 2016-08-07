package com.teamawesome.navii.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by JMtorii on 16-08-06.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoyagerResponse {

    @JsonProperty(value = "voyager")
    private User user;

    @JsonProperty(value = "token")
    private String token;

    public VoyagerResponse() {}

    private VoyagerResponse(Builder builder) {
        this.user = builder.user;
        this.token = builder.token;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public static class Builder {
        private User user;
        private String token;

        public Builder() {}

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public VoyagerResponse build() {
            return new VoyagerResponse(this);
        }
    }
}
