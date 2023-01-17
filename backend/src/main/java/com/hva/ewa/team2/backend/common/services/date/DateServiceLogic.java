package com.hva.ewa.team2.backend.common.services.date;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface DateServiceLogic {

    LocalDateTime currentDay(int dayOffset, int hour, int minute);

    LocalDateTime currentDay(int hour, int minute);

    boolean isThisMonth(LocalDateTime date);

    boolean isThisWeek(LocalDateTime date);

    List<LocalDate> getAllDaysOfTheWeek(int weekNumber);

}
