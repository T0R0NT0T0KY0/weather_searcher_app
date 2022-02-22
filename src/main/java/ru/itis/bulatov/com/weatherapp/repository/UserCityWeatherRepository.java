package ru.itis.bulatov.com.weatherapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.itis.bulatov.com.weatherapp.model.UserCityWeatherRequest;

import java.util.Optional;

public interface UserCityWeatherRepository extends CrudRepository<UserCityWeatherRequest, Long> {
	Optional<UserCityWeatherRequest[]> findAllByUser_Id(long user_id);
}
