package com.projects.dfg_team.weather;

/**
 * Created by georgetsd on 27/7/15.
 */
public class CurrentWeather {

    private String mIcon;
    private long mTime;
    private double mTemparture;
    private double mHumidity;
    private double mPrecipChanche;
    private String mSummary;


    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public double getTemparture() {
        return mTemparture;
    }

    public void setTemparture(double temparture) {
        mTemparture = temparture;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public double getPrecipChanche() {
        return mPrecipChanche;
    }

    public void setPrecipChanche(double precipChanche) {
        mPrecipChanche = precipChanche;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }
}
