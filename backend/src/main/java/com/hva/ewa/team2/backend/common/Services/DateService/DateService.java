package com.hva.ewa.team2.backend.common.Services.DateService;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DateService implements DateServiceLogic {

    @Override
    public LocalDateTime currentDay(int dayOffset, int hour, int minute) {
        LocalTime time = LocalTime.of(hour, minute);

        if (dayOffset == 0) {
            return currentDay(hour, minute);
        } else if (dayOffset > 0) {
            return LocalDateTime.now().plusDays(dayOffset).with(time);
        } else {
            return LocalDateTime.now().minusDays(dayOffset).with(time);
        }
    }

    @Override
    public LocalDateTime currentDay(int hour, int minute) {
        LocalTime time = LocalTime.of(hour, minute);
        return LocalDateTime.now().with(time);
    }
}
