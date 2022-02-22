package ru.itis.bulatov.com.weatherapp.dto.cityWeatherResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainInformation {
	private float temp;
	private int pressure;
	private int humidity;
}
