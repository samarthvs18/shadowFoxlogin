package com.example.shadowfoxlogin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    private EditText etCity;
    private Button btnGetWeather, btnSaveLocation, btnViewSavedLocations;
    private TextView tvWeatherResult;
    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter; // Removed redundant package name
    private List<String> savedLocations;

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String API_KEY = "09ef35f9d23e137ea2bf4caf6869beab";
    private static final String PREFS_NAME = "WeatherPrefs";
    private static final String KEY_LOCATIONS = "SavedLocations";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etCity = findViewById(R.id.etCity);
        btnGetWeather = findViewById(R.id.btnGetWeather);
        btnSaveLocation = findViewById(R.id.btnSaveLocation);
        btnViewSavedLocations = findViewById(R.id.btnViewSavedLocations);
        tvWeatherResult = findViewById(R.id.tvWeatherResult);
        recyclerView = findViewById(R.id.recyclerViewSavedLocations);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        savedLocations = new ArrayList<>();
        locationAdapter = new LocationAdapter(savedLocations);
        recyclerView.setAdapter(locationAdapter);

        loadSavedLocations(); // Ensure adapter is set up before calling notifyDataSetChanged()

        btnGetWeather.setOnClickListener(v -> {
            String city = etCity.getText().toString().trim();
            if (!city.isEmpty()) {
                getWeather(city);
                etCity.setText(""); // Clear input
            } else {
                Toast.makeText(WeatherActivity.this, "Enter a city name", Toast.LENGTH_SHORT).show();
            }
        });

        btnSaveLocation.setOnClickListener(v -> {
            String city = etCity.getText().toString().trim();
            if (!city.isEmpty()) {
                saveLocation(city);
            } else {
                Toast.makeText(WeatherActivity.this, "Enter a city name to save", Toast.LENGTH_SHORT).show();
            }
        });

        btnViewSavedLocations.setOnClickListener(v -> loadSavedLocations());
    }

    private void getWeather(String city) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiService weatherApi = retrofit.create(WeatherApiService.class);

        Call<WeatherResponse> call = weatherApi.getWeather(city, API_KEY, "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weather = response.body();
                    String result = "City: " + weather.getCityName() + "\nTemperature: " + weather.getMain().getTemp() + "°C";
                    tvWeatherResult.setText(result);
                } else {
                    tvWeatherResult.setText("Invalid city name or API issue.");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                tvWeatherResult.setText("Error: could not fetch result " + t.getMessage());
            }
        });
    }

    private void saveLocation(String city) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> locations = new HashSet<>(prefs.getStringSet(KEY_LOCATIONS, new HashSet<>()));

        if (locations.contains(city)) {
            Toast.makeText(this, "Location already saved", Toast.LENGTH_SHORT).show();
            return;
        }

        locations.add(city);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(KEY_LOCATIONS, locations);
        editor.apply();

        Toast.makeText(this, "Location saved", Toast.LENGTH_SHORT).show();
        loadSavedLocations();
    }

    private void loadSavedLocations() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> locations = prefs.getStringSet(KEY_LOCATIONS, new HashSet<>());

        savedLocations.clear();
        savedLocations.addAll(locations);

        if (locationAdapter != null) {
            locationAdapter.notifyDataSetChanged();
        }
    }
}


