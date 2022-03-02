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
@Table(name = "city_weathers_requests")
public class CityWeather {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private long id;

	@ManyToOne
	@JoinColumn(name = "city_id", nullable = false)
	@ToString.Exclude
	private City city;

	@Column(nullable = false, columnDefinition="TEXT")
	private String precipitation;

	@Column(nullable = false, columnDefinition="TEXT")
	private String description;

	@Column(nullable = false)
	private int pressure;

	@Column(nullable = false)
	private int humidity;

	@Column(nullable = false)
	private float temperature;

	@Column(nullable = false, name = "wind_speed")
	private float windSpeed;

	@Column(nullable = false, name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();
}
