package com.hva.ewa.team2.backend.common.services.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DateServiceLogic {

    LocalDateTime currentDay(int dayOffset, int hour, int minute);

    LocalDateTime currentDay(int hour, int minute);

    boolean isThisMonth(LocalDateTime date);

    boolean isThisWeek(LocalDateTime date);

    List<LocalDate> getAllDaysOfTheWeek(int weekNumber);

}
