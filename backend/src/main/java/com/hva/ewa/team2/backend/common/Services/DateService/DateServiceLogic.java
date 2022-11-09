package com.hva.ewa.team2.backend.common.Services.DateService;

import java.time.LocalDateTime;

public interface DateServiceLogic {
    LocalDateTime currentDay(int dayOffset, int hour, int minute);
    LocalDateTime currentDay(int hour, int minute);
}
