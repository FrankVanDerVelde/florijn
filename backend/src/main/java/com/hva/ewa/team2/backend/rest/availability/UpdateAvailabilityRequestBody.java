package com.hva.ewa.team2.backend.rest.availability;

import lombok.Getter;
import lombok.Setter;

public class UpdateAvailabilityRequestBody {

    @Getter @Setter
    private int id;
    @Getter @Setter
    private String date;
    @Getter @Setter
    private String from;
    @Getter @Setter
    private String to;

    public UpdateAvailabilityRequestBody(int id, String date, String from, String to) {
        this.id = id;
        this.date = date;
        this.from = from;
        this.to = to;
    }
}
