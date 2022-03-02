package ru.itis.bulatov.com.weatherapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.itis.bulatov.com.weatherapp.model.UserCityWeatherRequest;

import java.util.List;
import java.util.Optional;

public interface UserRequestsRepository extends CrudRepository<UserCityWeatherRequest, Long> {
	List<UserCityWeatherRequest> findAllByUserId(long userId);
	List<UserCityWeatherRequest> findAllByWeatherCityCityName(String cityName);
	Optional<UserCityWeatherRequest> findAllByOrderByCreatedAtDesc();
}
