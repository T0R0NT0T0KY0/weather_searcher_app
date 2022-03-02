package ru.itis.bulatov.com.weatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.bulatov.com.weatherapp.dto.cityWeatherResponse.CityWeatherResponse;
import ru.itis.bulatov.com.weatherapp.model.City;
import ru.itis.bulatov.com.weatherapp.model.CityWeather;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherInformationDTO {
	private long cityId;

	private String cityName;

	private String precipitation;
	private String description;
	private int humidity;
	private float temperature;
	private int pressure;
	private float windSpeed;
	private LocalDateTime createdAt = LocalDateTime.now();

	public static WeatherInformationDTO fromCityWeather(CityWeather cityWeather) {
		return new WeatherInformationDTO(cityWeather.getCity().getId(), cityWeather.getCity().getCityName(),
				cityWeather.getPrecipitation(), cityWeather.getDescription(), cityWeather.getHumidity(),
				cityWeather.getTemperature(), cityWeather.getPressure(),
				cityWeather.getWindSpeed(), cityWeather.getCreatedAt());
	}
	public static CityWeather toCityWeather(CityWeatherResponse cityWeather, City city) {
		return CityWeather.builder()
				.city(city)
				.precipitation(cityWeather.getWeather().get(0).getMain())
				.description(cityWeather.getWeather().get(0).getDescription())
				.pressure(cityWeather.getMain().getPressure())
				.humidity(cityWeather.getMain().getHumidity())
				.temperature(cityWeather.getMain().getTemp())
				.windSpeed(cityWeather.getWind().getSpeed())
				.createdAt(LocalDateTime.now())
				.build();
	}

}
