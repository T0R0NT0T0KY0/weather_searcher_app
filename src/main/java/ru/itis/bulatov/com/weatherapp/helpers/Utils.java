package ru.itis.bulatov.com.weatherapp.helpers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Utils {
	public static boolean isNotEmptyString(String str) {
		return Objects.nonNull(str) && str.trim().length() != 0;
	}
	public static boolean isEmptyString(String str) {
		return !isNotEmptyString(str);
	}

	public static String fromListToString(List<String> list, String separator, String defaultVar) {
		if (list.size() <= 0) return defaultVar;
		Optional<String> reduce = list.stream().reduce((prev, current) -> String.format("%s\n%s", prev, current));
		return reduce.get();
	}

	public static String fromListToString(List<String> list) {
		return fromListToString(list, "\n", null);
	}
}
