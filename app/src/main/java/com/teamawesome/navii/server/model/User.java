package com.teamawesome.navii.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by JMtorii on 2015-10-21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @JsonProperty(value = "user_id")
    private int userId;

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "salt")
    private String salt;

    @JsonProperty(value = "is_facebook")
    private boolean isFacebook;

    @JsonProperty(value = "verified")
    private boolean verified;

    public User() {}

    private User(Builder builder) {
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.salt = builder.salt;
        this.isFacebook = builder.isFacebook;
        this.verified = builder.verified;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean getVerified() {
        return verified;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public boolean isFacebook() {
        return isFacebook;
    }

    public static class Builder {
        private String username;
        private String email;
        private String password;
        private String salt;
        private boolean isFacebook;
        private boolean verified;

        public Builder() {}

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder salt(String salt) {
            this.salt = salt;
            return this;
        }

        public Builder isFacebook(boolean isFacebook) {
            this.isFacebook = isFacebook;
            return this;
        }

        public Builder verified(boolean verified) {
            this.verified = verified;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
