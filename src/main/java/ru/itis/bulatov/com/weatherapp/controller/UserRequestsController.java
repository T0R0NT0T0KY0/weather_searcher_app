package ru.itis.bulatov.com.weatherapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.bulatov.com.weatherapp.dto.UserRequestsDTO;
import ru.itis.bulatov.com.weatherapp.helpers.DoubleValue;
import ru.itis.bulatov.com.weatherapp.servise.UserWeatherService;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/weather/requests")
public class UserRequestsController {
	@Autowired
	private UserWeatherService userWeatherService;

	@GetMapping("/users/{user_id}")
	public ResponseEntity getUserRequests(@PathVariable("user_id") long userId) {
		UserRequestsController.log.info("user_id={}", userId);

		DoubleValue<List<UserRequestsDTO>, String> dataError = userWeatherService.getRequestsByUserId(userId);

		if (Objects.nonNull(dataError.getError())) {return ResponseEntity.status(400).body(dataError.getError());}

		if (dataError.getData().size() == 0)
			return ResponseEntity.notFound().build();

		System.out.println(dataError);
		return ResponseEntity.ok(dataError.getData());
	}

	@GetMapping("/cites")
	public ResponseEntity getCityRequests(@RequestParam("name") String cityName) {
		UserRequestsController.log.info("city_name={}", cityName);

		DoubleValue<List<UserRequestsDTO>, String> dataError = userWeatherService.getRequestsByCityId(cityName);

		if (Objects.nonNull(dataError.getError())) {return ResponseEntity.status(400).body(dataError.getError());}

		if (dataError.getData().size() == 0)
			return ResponseEntity.notFound().build();

		System.out.println(dataError);
		return ResponseEntity.ok(dataError.getData());
	}

}
