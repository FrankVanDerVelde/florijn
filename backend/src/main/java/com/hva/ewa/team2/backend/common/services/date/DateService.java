package com.hva.ewa.team2.backend.common.services.date;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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


    private static LocalDate getFirstDayOfWeek(int weekNumber) {
        return LocalDate
                .of(Year.now().getValue(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth())
                .with(WeekFields.of(Locale.GERMANY).getFirstDayOfWeek())
                .with(WeekFields.of(Locale.GERMANY).weekOfWeekBasedYear(), weekNumber);
    }

    @Override
    public List<LocalDate> getAllDaysOfTheWeek(int weekNumber) {
        LocalDate firstDayOfWeek = getFirstDayOfWeek(weekNumber);
        return IntStream
                .rangeClosed(0, 6)
                .mapToObj(firstDayOfWeek::plusDays)
                .collect(Collectors.toList());
    }

}
