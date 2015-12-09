package com.teamawesome.navii.server.model;

import java.util.List;

/**
 * Created by sjung on 10/11/15.
 */
public class UserPreference {
    private List<Preference> preferences;
    private String username;

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserPreference() {}

    public UserPreference(Builder builder) {

    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {

        private List<String> preferences;
        private String username;


        public Builder() {}

        public Builder preferences(List<Preference> preferences) {
            this.preferences = preferences;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }


        // TODO: potentially check for non-null values
        public UserPreference build() {
            return new UserPreference(this);
        }
    }
}

