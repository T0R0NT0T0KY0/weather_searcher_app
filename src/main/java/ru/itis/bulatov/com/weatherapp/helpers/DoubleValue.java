package ru.itis.bulatov.com.weatherapp.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoubleValue<T, F> {
	private T data;
	private F error;


	public DoubleValue<T, F> setData(T data) {
		this.data = data;
		return this;
	}

	public DoubleValue<T, F> setError(F error) {
		this.error = error;
		return this;
	}
}
