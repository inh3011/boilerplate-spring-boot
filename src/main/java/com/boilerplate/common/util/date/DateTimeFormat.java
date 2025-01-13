package com.boilerplate.common.util.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormat {
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE = "yyyy-MM-dd";
    public static final String TIME = "HH:mm:ss";

    public static String getDateFormat(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 HH:mm");
        return localDateTime.format(formatter);
    }

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
