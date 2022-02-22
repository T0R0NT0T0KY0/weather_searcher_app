package ru.itis.bulatov.com.weatherapp.servise;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.itis.bulatov.com.weatherapp.dto.cityWeatherResponse.CityWeatherResponse;
import ru.itis.bulatov.com.weatherapp.dto.WeatherInformationDTO;
import ru.itis.bulatov.com.weatherapp.helpers.DoubleValue;
import ru.itis.bulatov.com.weatherapp.model.City;
import ru.itis.bulatov.com.weatherapp.model.CityWeather;
import ru.itis.bulatov.com.weatherapp.model.UserCityWeatherRequest;
import ru.itis.bulatov.com.weatherapp.repository.CityRepository;
import ru.itis.bulatov.com.weatherapp.repository.CityWeatherRepository;
import ru.itis.bulatov.com.weatherapp.repository.UserCityWeatherRepository;
import ru.itis.bulatov.com.weatherapp.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.itis.bulatov.com.weatherapp.helpers.LoggerHelper.log;
import static ru.itis.bulatov.com.weatherapp.helpers.Utils.*;

@Service
@RequiredArgsConstructor
public class UserWeatherService {
	@Value("${owm.api.key}")
	private String apiKey;
	private final RestTemplate restTemplate;
	private final UserRepository userRepository;
	private final CityRepository cityRepository;
	private final CityWeatherRepository cityWeatherRepository;
	private final UserCityWeatherRepository userCityWeatherRepository;

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

			CityWeather weatherRequest = WeatherInformationDTO.toCityWeather(weatherObject,
					cityRepository.findByCityName(city).get());

			CityWeather savedCity = cityWeatherRepository.save(weatherRequest);

			userCityWeatherRepository.save(UserCityWeatherRequest.builder()
					.user(userRepository.findByEmail(email).get())
					.weather(savedCity)
					.createdAt(LocalDate.now())
					.build());

			WeatherInformationDTO weatherInformationDTO = WeatherInformationDTO.fromCityWeather(savedCity);

			doubleValue.setData(weatherInformationDTO);

			return doubleValue;
		} catch (Error e) {
			e.printStackTrace();
			log.info(e.getLocalizedMessage());
			errDetails.add(e.getLocalizedMessage());
		}


		return doubleValue.setError(fromListToString(errDetails));
	}

	public List<String> validateGetWeatherByCityName(String city, String email) {
		List<String> errDetails = new ArrayList<>();
		if (isEmptyString(city)) {errDetails.add("Название города не передано");}
		if (userRepository.findByEmail(email).isEmpty()) {errDetails.add("Пользователь не найден");}

		if (cityRepository.findByCityName(city).isEmpty()) {
			cityRepository.save(City.builder()
					.cityName(city)
					.createdAt(LocalDate.now())
					.build());
		}

		return errDetails;
	}

}
