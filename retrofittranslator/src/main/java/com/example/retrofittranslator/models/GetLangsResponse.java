package com.example.retrofittranslator.models;

import java.util.Map;

/**
 * Created by ASUS on 17.01.2018.
 */

public class GetLangsResponse {
    String[] dirs;
    Map<String, String> langs;

    public GetLangsResponse(String[] dirs, Map langs) {
        this.dirs = dirs;
        this.langs = langs;
    }

    public String[] getDirs() {
        return dirs;
    }

    public void setDirs(String[] dirs) {
        this.dirs = dirs;
    }

    public Map<String, String> getLangs() {
        return langs;
    }

    public void setLangs(Map langs) {
        this.langs = langs;
    }
}
