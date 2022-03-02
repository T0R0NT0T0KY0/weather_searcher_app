package ru.itis.bulatov.com.weatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.bulatov.com.weatherapp.model.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
	private long userId;

	private String email;

	private LocalDateTime createdAt;

	public static UserDTO fromUser(User user) {
		return new UserDTO(user.getId(), user.getEmail(), user.getCreatedAt());
	}

}
