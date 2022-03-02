package ru.itis.bulatov.com.weatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.bulatov.com.weatherapp.model.City;
import ru.itis.bulatov.com.weatherapp.model.CityWeather;
import ru.itis.bulatov.com.weatherapp.model.UserCityWeatherRequest;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestsDTO {
	private long requestId;
	private long userId;
	private WeatherInformationDTO weatherInformation;
	private LocalDateTime createdAt;

	public static UserRequestsDTO fromUserCityWeatherRequest(UserCityWeatherRequest request) {
		return new UserRequestsDTO(request.getId(), request.getUser().getId(),
				WeatherInformationDTO.fromCityWeather(request.getWeather()),
				request.getCreatedAt());
	}

}
