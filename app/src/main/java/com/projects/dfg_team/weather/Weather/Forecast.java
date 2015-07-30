package com.projects.dfg_team.weather.Weather;

/**
 * Created by georgetsd on 29/7/15.
 */
public class Forecast {

    private Current mCurrent;
    private Daily[] mDaily;
    private Hour[] mHours;

    public Current getCurrent() {
        return mCurrent;
    }

    public void setCurrent(Current current) {
        mCurrent = current;
    }

    public Daily[] getDaily() {
        return mDaily;
    }

    public void setDaily(Daily[] daily) {
        mDaily = daily;
    }

    public Hour[] getHours() {
        return mHours;
    }

    public void setHours(Hour[] hours) {
        mHours = hours;
    }
}
