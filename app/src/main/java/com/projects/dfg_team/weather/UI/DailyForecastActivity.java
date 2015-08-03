package com.projects.dfg_team.weather.UI;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.projects.dfg_team.weather.Adapters.DayAdapter;
import com.projects.dfg_team.weather.R;
import com.projects.dfg_team.weather.Weather.Day;

import java.util.Arrays;

public class DailyForecastActivity extends ListActivity {

    private Day[] mDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);



        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);

        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);

        DayAdapter adapter = new DayAdapter(this, mDays);
        setListAdapter(adapter);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String daysOfTheWeek = mDays[position].getDayOfTheWeek();
        String temperatureMax = mDays[position].getTemperatureMax() + "";
        String condition = mDays[position].getSummary();

        String message = String.format("On %s the high will be %s and it will be %s", daysOfTheWeek, temperatureMax, condition);

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
