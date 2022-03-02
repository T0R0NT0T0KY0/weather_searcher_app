package ru.itis.bulatov.com.weatherapp.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_city_weathers_requests")
public class UserCityWeatherRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@ToString.Exclude
	private User user;

	@ManyToOne
	@JoinColumn(name = "city_id", nullable = false)
	@ToString.Exclude
	private CityWeather weather;


	@Column(nullable = false, name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();
}
