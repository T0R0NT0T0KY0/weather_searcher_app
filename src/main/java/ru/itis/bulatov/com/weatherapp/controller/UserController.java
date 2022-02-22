package ru.itis.bulatov.com.weatherapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.bulatov.com.weatherapp.dto.CreateUserDTO;
import ru.itis.bulatov.com.weatherapp.dto.UserDTO;
import ru.itis.bulatov.com.weatherapp.helpers.DoubleValue;
import ru.itis.bulatov.com.weatherapp.servise.UserService;

import javax.validation.Valid;
import java.util.Objects;

import static ru.itis.bulatov.com.weatherapp.helpers.LoggerHelper.log;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity getUsers(@Valid @RequestBody CreateUserDTO createUserDTO) {
		log.info(createUserDTO.toString());

		DoubleValue<UserDTO, String> dataError = userService.createUser(createUserDTO);
		String error = dataError.getError();
		if (Objects.nonNull(error)) {return ResponseEntity.badRequest().body(error);}

		UserDTO user = dataError.getData();
		return ResponseEntity.ok(user);
	}

	@GetMapping("/{id}")
	public ResponseEntity getUsersById(@PathVariable("id") long id) {
		log.info("user_id={}", id);
		DoubleValue<UserDTO, String> dataError = userService.getUserById(id);

		if (Objects.nonNull(dataError.getError())) {return ResponseEntity.status(404).body(dataError.getError());}

		return ResponseEntity.ok(dataError.getData());
	}

	@GetMapping
	public ResponseEntity getUsersByEmail(@RequestParam("email") String email) {
		DoubleValue<UserDTO, String> dataError = userService.getUserByEmail(email);

		if (Objects.nonNull(dataError.getError())) {return ResponseEntity.status(404).body(dataError.getError());}

		return ResponseEntity.ok(dataError.getData());
	}
}
