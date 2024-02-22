package org.example.helpers;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeParseHelper {
    public static Instant convertToUnixDate(String input) {
        input= input.toLowerCase();

        Pattern pattern = Pattern.compile("(\\d+)\\s*(seconds?|minutes?|hour|hours?|weeks?|months?|years?)");
        Matcher matcher = pattern.matcher(input);

        if(input.indexOf("about an minute")!= -1) return Instant.now().minus(1, ChronoUnit.MINUTES);
        if(input.indexOf("about an hour")!= -1) return Instant.now().minus(1, ChronoUnit.HOURS);
        if(input.indexOf("about an day")!= -1) return Instant.now().minus(1, ChronoUnit.DAYS);
        if(input.indexOf("about an month")!= -1) return Instant.now().minus(1, ChronoUnit.MONTHS);
        if(input.indexOf("about an year")!= -1) return Instant.now().minus(1, ChronoUnit.YEARS);

        if (matcher.find()) {
            int amount = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "second":
                case "seconds":
                    return Instant.now().minus(amount, ChronoUnit.SECONDS);
                case "minute":
                case "minutes":
                    return Instant.now().minus(amount, ChronoUnit.MINUTES);
                case "hour":
                case "hours":
                    return Instant.now().minus(amount, ChronoUnit.HOURS);
                case "week":
                case "weeks":
                    return Instant.now().minus(amount * 7, ChronoUnit.DAYS);
                case "month":
                case "months":
                    return Instant.now().minus(amount, ChronoUnit.MONTHS);
                case "year":
                case "years":
                    return Instant.now().minus(amount, ChronoUnit.YEARS);
                default:
                    throw new IllegalArgumentException("Invalid time unit: " + unit);
            }
        } else {
            throw new IllegalArgumentException("Invalid input format");
        }
    }

    public static String convertToString(Instant instant) {
        if(instant==null)return  "NA";

        Instant now = Instant.now();
        Duration duration = Duration.between(instant, now);

        long seconds = duration.getSeconds();

        if (seconds < 60) {
            return seconds + "s";
        } else if (seconds < 60 * 60) {
            long minutes = seconds / 60;
            long remainingSeconds = seconds % 60;
            return minutes + "m" + (remainingSeconds > 0 ? "+" : "");
        } else if (seconds < 60 * 60 * 24) {
            long hours = seconds / (60 * 60);
            long remainingMinutes = (seconds % (60 * 60)) / 60;
            return hours + "h" + (remainingMinutes > 0 ? "+" : "");
        } else if (seconds < 60 * 60 * 24 * 7) {
            long days = seconds / (60 * 60 * 24);
            return days + "d";
        } else if (seconds < 60 * 60 * 24 * 30) {
            long weeks = seconds / (60 * 60 * 24 * 7);
            return weeks + "w";
        } else {
            long months = seconds / (60 * 60 * 24 * 30);
            return months + "M";
        }
    }
}
