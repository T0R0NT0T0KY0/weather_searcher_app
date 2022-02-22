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
@Table(name = "d_users")
public class DUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@ToString.Exclude
	private User user;

	@Column(nullable = false, columnDefinition="TEXT")
	private String password;

	@Column(nullable = false, name = "created_at")
	private LocalDate createdAt = LocalDate.now();

}
