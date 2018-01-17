package com.example.retrofittranslator.models;

import java.util.Map;

/**
 * Created by ASUS on 17.01.2018.
 */

public class TranslateResponse {
    int code;
    String lang;
    Map<String, String> detected;

    public Map<String, String> getDetected() {
        return detected;
    }

    public void setDetected(Map<String, String> detected) {
        this.detected = detected;
    }

    String[] text;

    public TranslateResponse(int code, String lang, String[] text) {
        this.code = code;
        this.lang = lang;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }
}
