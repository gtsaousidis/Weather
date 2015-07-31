package com.projects.dfg_team.weather.Weather;

import com.projects.dfg_team.weather.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by georgetsd on 27/7/15.
 */
public class Current {

    private String mIcon;
    private long mTime;
    private double mTemparture;
    private double mHumidity;
    private double mPrecipChanche;
    private String mSummary;
    private String mTimeZone;

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getIconId(){
        return Forecast.getIconId(mIcon);
    }

    public long getTime() {
        return mTime;
    }

    //Formatted Time//
    public String getFormattedTime(){

        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime() * 1000);
        String timeString = formatter.format(dateTime);

        return timeString;

    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getTemparture() {
        return (int)Math.round((mTemparture-32)*5/9);
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

    public int getPrecipChanche() {
        double precipPercentage = mPrecipChanche * 100;

        return (int)Math.round(precipPercentage);
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
