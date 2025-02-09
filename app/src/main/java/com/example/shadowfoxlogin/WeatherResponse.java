package com.example.shadowfoxlogin;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    private String cityName;

    @SerializedName("main")
    private Main main;

    public String getCityName() {
        return cityName;
    }

    public Main getMain() {
        return main;
    }

    public class Main{
        @SerializedName("temp")
        private double temp;

        public double getTemp() {
            return temp;
        }
    }


}
