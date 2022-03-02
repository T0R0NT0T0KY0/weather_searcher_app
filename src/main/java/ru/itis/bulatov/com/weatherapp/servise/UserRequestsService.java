package ru.itis.bulatov.com.weatherapp.servise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.bulatov.com.weatherapp.repository.UserRequestsRepository;

@Service
@RequiredArgsConstructor
public class UserRequestsService {
	private final UserRequestsRepository userRequestsRepository;

}
