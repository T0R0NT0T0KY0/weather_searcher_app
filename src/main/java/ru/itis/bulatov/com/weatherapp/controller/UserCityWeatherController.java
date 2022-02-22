package ru.itis.bulatov.com.weatherapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.bulatov.com.weatherapp.dto.UserDTO;
import ru.itis.bulatov.com.weatherapp.dto.WeatherInformationDTO;
import ru.itis.bulatov.com.weatherapp.helpers.DoubleValue;
import ru.itis.bulatov.com.weatherapp.servise.CityWeatherService;
import ru.itis.bulatov.com.weatherapp.servise.UserService;
import ru.itis.bulatov.com.weatherapp.servise.UserWeatherService;

import java.util.Objects;

import static ru.itis.bulatov.com.weatherapp.helpers.LoggerHelper.log;
import static ru.itis.bulatov.com.weatherapp.helpers.Utils.isEmptyString;
import static ru.itis.bulatov.com.weatherapp.helpers.Utils.isNotEmptyString;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/weather/city")
public class UserCityWeatherController {

	@Autowired
	private UserWeatherService userWeatherService;
	@Autowired
	private UserService userService;


	@GetMapping
	public ResponseEntity getWeather(@RequestParam("city") String city, @RequestHeader("Authorization") String email) {
		log.info("city={}", city);
		log.info("email={}", email);

		if (isEmptyString(email)) {
			return ResponseEntity.status(401).body("Отказ в доступе");
		}

		DoubleValue<WeatherInformationDTO, String> dataError = userWeatherService.getWeatherByCityName(city, email);

		if (Objects.nonNull(dataError.getError())) {return ResponseEntity.status(400).body(dataError.getError());}

		return ResponseEntity.ok(dataError.getData());
	}

}
