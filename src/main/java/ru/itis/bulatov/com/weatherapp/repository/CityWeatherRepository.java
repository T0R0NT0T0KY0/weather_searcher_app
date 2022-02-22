package ru.itis.bulatov.com.weatherapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itis.bulatov.com.weatherapp.model.CityWeather;

import java.util.Optional;

@Repository
public interface CityWeatherRepository extends CrudRepository<CityWeather, Long> {

	Optional<CityWeather> findByCity_CityName(String cityName);
}
