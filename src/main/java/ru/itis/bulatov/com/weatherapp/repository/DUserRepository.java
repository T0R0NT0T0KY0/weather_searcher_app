package ru.itis.bulatov.com.weatherapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itis.bulatov.com.weatherapp.model.DUser;
@Repository
public interface DUserRepository extends CrudRepository<DUser, Long> {
}
