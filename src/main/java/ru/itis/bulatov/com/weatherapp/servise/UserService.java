package ru.itis.bulatov.com.weatherapp.servise;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.bulatov.com.weatherapp.dto.CreateUserDTO;
import ru.itis.bulatov.com.weatherapp.dto.UserDTO;
import ru.itis.bulatov.com.weatherapp.helpers.DoubleValue;
import ru.itis.bulatov.com.weatherapp.model.DUser;
import ru.itis.bulatov.com.weatherapp.model.User;
import ru.itis.bulatov.com.weatherapp.repository.DUserRepository;
import ru.itis.bulatov.com.weatherapp.repository.UserRepository;

import static ru.itis.bulatov.com.weatherapp.helpers.Utils.fromListToString;
import static ru.itis.bulatov.com.weatherapp.helpers.Utils.isNotEmptyString;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserRepository userRepository;
	private final DUserRepository dUserRepository;
	private final MessageDigest digester;

	@Transactional
	public DoubleValue<UserDTO, String> createUser(CreateUserDTO createUserDto) {
		DoubleValue<UserDTO, String> dataError = new DoubleValue<>();
		try {
			String errors = validateCreateUser(createUserDto);
			dataError.setError(errors);

			User newUser = User.builder()
					.email(createUserDto.getEmail())
					.createdAt(LocalDateTime.now())
					.build();

			DUser newDUser = DUser.builder()
					.user(newUser)
					.password(new String(
							digester.digest(createUserDto.getPassword().getBytes(StandardCharsets.UTF_8))))
					.createdAt(LocalDateTime.now())
					.build();

			User savedUser = userRepository.save(newUser);
			dUserRepository.save(newDUser);

			dataError.setData(UserDTO.fromUser(savedUser));
			return dataError;
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getLocalizedMessage());
			dataError.setError(e.getLocalizedMessage());
			return dataError;
		}
	}

	public String validateCreateUser(CreateUserDTO createUserDto) {
		List<String> errors = new ArrayList<>();

		String email = createUserDto.getEmail();
		String password = createUserDto.getPassword();

		if (!isNotEmptyString(email)) {
			errors.add("Имя пользователя не может быть пустым");
		} else if (userRepository.findByEmail(email).isPresent()) {
			errors.add(String.format("Пользователь с ником '%s' уже сущствует", email));
		}
		int minPasswordLength = 8;

		if (!isNotEmptyString(password)) {
			errors.add("Пароль не может быть пустым");
		} else if (password.length() <= minPasswordLength) {
			errors.add(String.format("Минимальная длина пароля %d символов", minPasswordLength));
		} else if (password.matches(
				"(?=.*[0-9]{2,})(?=.*[a-zа-яё]{2,})(?=.*[A-ZА-ЯЁ]{2,})(?=.*[@#$%^&+=]).{" + minPasswordLength + ",}")) {
			errors.add("В пороле должно быть минимум: \n" +
					" • 2 Цыфры.\n" +
					" • 2 Буквы нижнего регистра.\n" +
					" • 2 Буквы верхнего регистра.");
		}
		return fromListToString(errors);
	}


	public DoubleValue<UserDTO, String> getUserByEmail(String email) {
		DoubleValue<UserDTO, String> dataError = new DoubleValue<>();
		try {
			Optional<User> optionalUserEntity = userRepository.findByEmail(email);
			if (optionalUserEntity.isEmpty()) {
				dataError.setError("Пользователь не найден");
				return dataError;
			}
			User user = optionalUserEntity.get();
			UserDTO userModel = UserDTO.fromUser(user);
			dataError.setData(userModel);
			return dataError;
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getLocalizedMessage());
			dataError.setError("Ошибка на сервере");
			return dataError;
		}
	}

	public DoubleValue<UserDTO, String> getUserById(long id) {
		DoubleValue<UserDTO, String> dataError = new DoubleValue<>();
		try {
			Optional<User> optionalUserEntity = userRepository.findById(id);
			if (optionalUserEntity.isEmpty()) {
				dataError.setError("Пользователь не найден");
				return dataError;
			}
			User user = optionalUserEntity.get();
			UserDTO userModel = UserDTO.fromUser(user);
			dataError.setData(userModel);
			return dataError;
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getLocalizedMessage());
			dataError.setError("Ошибка на сервере");
			return dataError;
		}
	}
}
