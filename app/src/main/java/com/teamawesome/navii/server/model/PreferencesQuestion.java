package com.teamawesome.navii.server.model;

import java.util.List;

/**
 * Created by sjung on 17/12/15.
 */
public class PreferencesQuestion {
    private String question;
    private List<Preference> preferences;

    private PreferencesQuestion(Builder builder) {
        this.question = builder.question;
        this.preferences = builder.preferences;
    }

    public PreferencesQuestion() {}

    public static Builder getBuilder() {
        return new Builder();
    }

    public String getQuestion() {
        return question;
    }

    public List<Preference> getPreferences() {
        return preferences;
    }

    public static class Builder {

        private String question;
        private List<Preference> preferences;

        public Builder() {}

        public Builder question(String question) {
            this.question = question;
            return this;
        }

        public Builder preferences(List<Preference> preferences) {
            this.preferences = preferences;
            return this;
        }

        public PreferencesQuestion build() {
            return new PreferencesQuestion(this);
        }
    }
}
