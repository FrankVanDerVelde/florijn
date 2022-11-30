package com.hva.ewa.team2.backend.common.services.date;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Component
public class DateService implements DateServiceLogic {

    @Override
    public LocalDateTime currentDay(int dayOffset, int hour, int minute) {
        LocalTime time = LocalTime.of(hour, minute);

        if (dayOffset == 0) {
            return currentDay(hour, minute);
        }
        return LocalDateTime.now().plusDays(dayOffset).with(time);
    }

    @Override
    public LocalDateTime currentDay(int hour, int minute) {
        LocalTime time = LocalTime.of(hour, minute);
        return LocalDateTime.now().with(time);
    }

    @Override
    public boolean isThisMonth(LocalDateTime date) {
        LocalDateTime now = LocalDateTime.now();

        return date.getMonth() == now.getMonth() && date.getYear() == now.getYear();
    }

    @Override
    public boolean isThisWeek(LocalDateTime date) {
        LocalDateTime now = LocalDateTime.now();
        final TemporalField weekField = WeekFields.of(Locale.GERMANY).weekOfWeekBasedYear();

        return date.get(weekField) == now.get(weekField) && date.getYear() == now.getYear();
    }
}
