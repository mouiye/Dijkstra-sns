package com.example.test.util;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeAgo {

    public static String timeAgo(Timestamp timestamp) {
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(dateTime, now);

        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long weeks = days / 7;
        long months = days / 30; // Approximation
        long years = days / 365; // Approximation

        if (seconds < 0) {
            seconds = 0;
        }

        if (seconds < 60) {
            return seconds + "초 전";
        } else if (minutes < 60) {
            return minutes + "분 전";
        } else if (hours < 24) {
            return hours + "시간 전";
        } else if (days < 7) {
            return days + "일 전";
        } else if (weeks < 4) {
            return weeks + "주 전";
        } else if (months < 12) {
            return months + "달 전";
        } else {
            return years + "년 전";
        }
    }
}