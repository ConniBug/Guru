package com.guru.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TimeFormatter {
	private static Map<Integer, String> timeUnit = new MapUtils<>(1, "second").put(60, "minute").put(3600, "hour").put(86400, "day").put(31536000, "year").put(Integer.MAX_VALUE, "null").get();
	
	public static String formatDuration(long seconds) {
			
		if(seconds == 0) {
			return "now";
		}
		
		List<Integer> keys = timeUnit.keySet().stream().sorted().collect(Collectors.toList());
		
		for(int i = 0; i < keys.size(); i++) {
			
			if(seconds < keys.get(i) || (i+1 > keys.size())) {
				
				long unit = 0;
				long unitMagnitude = keys.get(i-1);

				unit = (seconds / unitMagnitude);
				seconds = seconds - (unitMagnitude * unit);
				
				String formatter = unit + " " + timeUnit.get(keys.get(i-1));
				
				if(unit > 1) {
					formatter += "s";
				}
				
				if(seconds == 0) {
					return formatter;
				}
				
				
				long amount = 0;
				long s2 = seconds;
				for(int k = i-(2); k >= 0; k--) {
					if(s2 >= keys.get(k)) {
						s2 = s2 - (keys.get(k) * (s2 / keys.get(k)));
						amount++;
					}
				}
				
				if(amount == 1) {
					return formatter + " and " + formatDuration(seconds);
				}
				
				return formatter + ", " + formatDuration(seconds);
			}
			
		}
		
		return "";
	}
}