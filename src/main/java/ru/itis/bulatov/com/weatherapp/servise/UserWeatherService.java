package ru.itis.bulatov.com.weatherapp.servise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.itis.bulatov.com.weatherapp.dto.UserRequestsDTO;
import ru.itis.bulatov.com.weatherapp.dto.cityWeatherResponse.CityWeatherResponse;
import ru.itis.bulatov.com.weatherapp.dto.WeatherInformationDTO;
import ru.itis.bulatov.com.weatherapp.helpers.DoubleValue;
import ru.itis.bulatov.com.weatherapp.model.City;
import ru.itis.bulatov.com.weatherapp.model.CityWeather;
import ru.itis.bulatov.com.weatherapp.model.User;
import ru.itis.bulatov.com.weatherapp.model.UserCityWeatherRequest;
import ru.itis.bulatov.com.weatherapp.repository.*;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.itis.bulatov.com.weatherapp.helpers.Utils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserWeatherService {
	@Value("${owm.api.key}")
	private String apiKey;
	private final RestTemplate restTemplate;
	private final UserRepository userRepository;
	private final CityRepository cityRepository;
	private final CityWeatherRepository cityWeatherRepository;
	private final UserCityWeatherRepository userCityWeatherRepository;
	private final UserRequestsRepository userRequestsRepository;

	@Transactional
	public DoubleValue<WeatherInformationDTO, String> getWeatherByCityName(String city, String email) {
		List<String> errDetails = new ArrayList<>();
		DoubleValue<WeatherInformationDTO, String> doubleValue = new DoubleValue<>();
		try {
			errDetails.addAll(validateGetWeatherByCityName(city, email));

			if (errDetails.size() > 0) {
				return doubleValue.setError(fromListToString(errDetails));
			}

			String url = String.format(
					"http://api.openweathermap.org/data/2.5/weather?appid=%s&q=%s", apiKey, city);

			CityWeatherResponse weatherObject = restTemplate.getForObject(url, CityWeatherResponse.class);

			if (Objects.isNull(weatherObject)) {
				return doubleValue.setError("Информация не найдена");
			}

			CityWeather savedCity = addCityWeather(weatherObject, city);


			WeatherInformationDTO weatherInformationDTO = addUserCityWeatherRequest(email, savedCity);

			doubleValue.setData(weatherInformationDTO);

			return doubleValue;
		} catch (Error e) {
			e.printStackTrace();
			log.info(e.getLocalizedMessage());
			errDetails.add(e.getLocalizedMessage());
		}


		return doubleValue.setError(fromListToString(errDetails));
	}

	private CityWeather addCityWeather(CityWeatherResponse weatherObject, String city) {
		Optional<City> byCityName = cityRepository.findByCityName(city);
		if (byCityName.isEmpty()) return null;

		CityWeather weatherRequest = WeatherInformationDTO.toCityWeather(weatherObject,
				byCityName.get());
		return cityWeatherRepository.save(weatherRequest);
	}

	private WeatherInformationDTO addUserCityWeatherRequest(String email, CityWeather savedCity) {

		Optional<User> userByEmail = userRepository.findByEmail(email);
		if (userByEmail.isEmpty()) return null;

		userCityWeatherRepository.save(UserCityWeatherRequest.builder()
				.user(userByEmail.get())
				.weather(savedCity)
				.createdAt(LocalDateTime.now())
				.build());
		return WeatherInformationDTO.fromCityWeather(savedCity);
	}

	public List<String> validateGetWeatherByCityName(String city, String email) {
		List<String> errDetails = new ArrayList<>();
		if (isEmptyString(city)) {errDetails.add("Название города не передано");}
		if (userRepository.findByEmail(email).isEmpty()) {errDetails.add("Пользователь не найден");}

		if (cityRepository.findByCityName(city).isEmpty()) {
			cityRepository.save(City.builder()
					.cityName(city)
					.createdAt(LocalDateTime.now())
					.build());
		}

		return errDetails;
	}

	public DoubleValue<List<UserRequestsDTO>, String> getRequestsByUserId(long userId) {
		List<String> errDetails = new ArrayList<>();
		DoubleValue<List<UserRequestsDTO>, String> doubleValue = new DoubleValue<>();
		try {
			errDetails.addAll(validateGetRequestsByUserId(userId));

			if (errDetails.size() > 0) return doubleValue.setError(fromListToString(errDetails));

			List<UserCityWeatherRequest> allByUserId = userRequestsRepository.findAllByUserId(userId);

			doubleValue.setData(
					allByUserId
							.stream()
							.map(UserRequestsDTO::fromUserCityWeatherRequest)
							.collect(Collectors.toList()));

			return doubleValue;
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getLocalizedMessage());
			errDetails.add(e.getLocalizedMessage());
		}
		return doubleValue.setError(fromListToString(errDetails));
	}

	public List<String> validateGetRequestsByUserId(long userId) {
		List<String> errDetails = new ArrayList<>();

		if (userRepository.findById(userId).isEmpty()) {errDetails.add("Пользователь не найден");}

		return errDetails;
	}

	public DoubleValue<List<UserRequestsDTO>, String> getRequestsByCityId(String cityName) {
		List<String> errDetails = new ArrayList<>();
		DoubleValue<List<UserRequestsDTO>, String> doubleValue = new DoubleValue<>();
		try {
			errDetails.addAll(validateGetRequestsByCityId(cityName));

			if (errDetails.size() > 0) return doubleValue.setError(fromListToString(errDetails));

			List<UserCityWeatherRequest> userRequests = userRequestsRepository.findAllByWeatherCityCityName(cityName);

			doubleValue.setData(userRequests
					.stream()
					.map(UserRequestsDTO::fromUserCityWeatherRequest)
					.collect(Collectors.toList()));

			return doubleValue;
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getLocalizedMessage());
			errDetails.add(e.getLocalizedMessage());
		}
		return doubleValue.setError(fromListToString(errDetails));
	}

	private Collection<String> validateGetRequestsByCityId(String cityName) {
		List<String> errDetails = new ArrayList<>();

		if (cityRepository.findByCityName(cityName).isEmpty()) {errDetails.add("Город не найден");}

		return errDetails;
	}
}

