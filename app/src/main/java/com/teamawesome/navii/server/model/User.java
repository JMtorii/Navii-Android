package com.teamawesome.navii.server.model;

/**
 * Created by JMtorii on 2015-10-21.
 */
public class User {

    private int id;
    private String username;
    private String password;
    private String salt;
    private Boolean isFacebook;

    public User() {}

    private User(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.salt = builder.salt;
        this.isFacebook = builder.isFacebook;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public Boolean isFacebook() {
        return isFacebook;
    }

    public static class Builder {
        private int id;
        private String username;
        private String password;
        private String salt;
        private Boolean isFacebook;

        public Builder() {}

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
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

        public Builder isFacebook(Boolean isFacebook) {
            this.isFacebook = isFacebook;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
