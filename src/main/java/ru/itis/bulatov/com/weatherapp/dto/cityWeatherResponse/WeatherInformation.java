package ru.itis.bulatov.com.weatherapp.dto.cityWeatherResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInformation {
	private long id;
	private String main;
	private String description;
}
