package ru.itis.bulatov.com.weatherapp.dto.cityWeatherResponse;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityWeatherResponse {
	private List<WeatherInformation> weather;
	private MainInformation main;
	private WindInformation wind;

}

