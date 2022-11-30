package com.hva.ewa.team2.backend.common.services.date;

import java.time.LocalDateTime;

public interface DateServiceLogic {

    LocalDateTime currentDay(int dayOffset, int hour, int minute);

    LocalDateTime currentDay(int hour, int minute);

    boolean isThisMonth(LocalDateTime date);

    boolean isThisWeek(LocalDateTime date);

}
