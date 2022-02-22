package ru.itis.bulatov.com.weatherapp.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cities")
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private long id;

	@Column(unique = true, nullable = false, columnDefinition="TEXT")
	private String cityName;


	@Column(nullable = false, name = "created_at")
	private LocalDate createdAt = LocalDate.now();

}
