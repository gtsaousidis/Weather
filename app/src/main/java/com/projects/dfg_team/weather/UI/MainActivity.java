package com.projects.dfg_team.weather.UI;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.dfg_team.weather.R;
import com.projects.dfg_team.weather.Weather.Current;
import com.projects.dfg_team.weather.Weather.Daily;
import com.projects.dfg_team.weather.Weather.Forecast;
import com.projects.dfg_team.weather.Weather.Hour;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends ActionBarActivity {

    //Property for Api key of forecast//
    String apiKey = "a5c52a34aac8cfd9804074bf46f199fa";

    public static  final String TAG = MainActivity.class.getSimpleName();

    private Forecast mForecast;

    //Adding annotation with ButterKnife //
    @Bind(R.id.timeLabel) TextView mTimeLabel;
    @Bind(R.id.temperatureLabel) TextView mTemperatureLabel;
    @Bind(R.id.humidityValue) TextView mHumidityValue;
    @Bind(R.id.precipeValue) TextView mPrecipeValue;
    @Bind(R.id.summaryLabel) TextView mSummaryLabel;
    @Bind(R.id.iconImageView) ImageView mIconImageView;
    @Bind(R.id.refreshImageView) ImageView mRefreshImageView;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.INVISIBLE);

        //Getting Cordinates//
        final LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getForecast(lm);


        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(lm);
            }
        });

    }

    //Checking for data//
    private void getForecast(LocationManager lm) {
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 20, locationListener);
        }
        else{
            Toast.makeText(this, R.string.error_gps, Toast.LENGTH_LONG).show();
        }
    }

    //Listening to hear the cordinates//

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double longtitude = location.getLongitude();
            double latitude = location.getLatitude();

            requestWeatherUpdates(latitude, longtitude);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    public void requestWeatherUpdates(double latitude, double longtitude){

        String forecastUrl = "https://api.forecast.io/forecast/"+ apiKey +"/"+ latitude + "," + longtitude;

        if (isNetworkAvailable()) {

            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecastUrl).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);

                        if (response.isSuccessful()) {
                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateData();
                                }
                            });

                        } else {
                            alertUserAboutError();
                        }

                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {

                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        }
        else{
            //alertUserAboutError();
            Toast.makeText(this, R.string.error_toast_network, Toast.LENGTH_LONG).show();
        }

        Log.d(TAG, "Main UI code is running!");
    }

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        }
        else {

            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    //Refreshing weather Data//
    private void updateData() {

        Current current = mForecast.getCurrent();

        //The double "" is an empty string so can pass the double value in text//
        mTemperatureLabel.setText(current.getTemparture()  + "");
        mTimeLabel.setText("At " + current.getFormattedTime() + " it will be:");
        mHumidityValue.setText(current.getHumidity() + "");
        mPrecipeValue.setText(current.getPrecipChanche() + "%");
        mSummaryLabel.setText(current.getSummary());

        Drawable drawable = getResources().getDrawable(current.getIconId());

        mIconImageView.setImageDrawable(drawable);

    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException{
        Forecast forecast = new Forecast();

        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setDaily(getDailyForecast(jsonData));
        //forecast.setHours(getHourlyForecast(jsonData));

        return forecast;

    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = new JSONObject("hourly");
        JSONArray dataHourly = new JSONArray("data");



        Hour[] hours = new Hour[dataHourly.length()];

        for (int i = 0; i < dataHourly.length(); i++){

            JSONObject jsonHour = dataHourly.getJSONObject(i);
            Hour hour = new Hour();

            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimezone(timezone);

            hours[i] = hour;

        }

        return hours;

    }

    private Daily[] getDailyForecast(String jsonData) {

        return new Daily[0];
    }

    private Current getCurrentDetails(String jsonData) throws JSONException{

        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        JSONObject currently = forecast.getJSONObject("currently");

        Current current = new Current();
        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setTemparture(currently.getDouble("temperature"));
        current.setIcon(currently.getString("icon"));
        current.setPrecipChanche(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTimeZone("timezone");


                Log.d(TAG, current.getFormattedTime());

        return current;
    }


    //Cheking for network availability//
    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()){

            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {

        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");

    }

}
