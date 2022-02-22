package ru.itis.bulatov.com.weatherapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itis.bulatov.com.weatherapp.model.City;

import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {
	Optional<City> findByCityName(String cityName);
}
