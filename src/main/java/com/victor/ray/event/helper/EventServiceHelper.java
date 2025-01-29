package com.victor.ray.event.helper;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class EventServiceHelper {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"; // Change this to your desired format
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public boolean isNumber(String str) {
        return str.matches("-?\\d+(\\.\\d+)?"); // matches integers and decimals
    }

    public static boolean isValidDateTime(String dateTimeStr) {
        try {
            // Try to parse the string into a LocalDateTime object
            LocalDateTime.parse(dateTimeStr, formatter);
            return true; // Parsing was successful
        } catch (DateTimeParseException e) {
            return false; // Parsing failed, invalid format
        }
    }
}
