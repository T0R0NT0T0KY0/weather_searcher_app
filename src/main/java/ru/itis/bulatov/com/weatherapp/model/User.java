package ru.itis.bulatov.com.weatherapp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private long id;

	@Email
	@Column(unique = true, nullable = false, columnDefinition="TEXT")
	private String email;


	@Column(nullable = false, name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();
}
