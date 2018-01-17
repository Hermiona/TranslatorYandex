package com.example.retrofittranslator.models;

/**
 * Created by ASUS on 18.01.2018.
 */

public class LangItem {
    String abbr;
    String description;

    public LangItem(String abbr, String description) {
        this.abbr = abbr;
        this.description = description;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
//        if (!(obj instanceof LangItem)) {
//            return false;
//        }
        LangItem other = (LangItem) obj;
        if (!this.abbr.equals(other.getAbbr())) {
            return false;
        }

        return true;
    }
}
