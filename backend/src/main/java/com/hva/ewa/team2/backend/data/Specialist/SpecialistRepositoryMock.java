package com.hva.ewa.team2.backend.data.Specialist;

import com.hva.ewa.team2.backend.domain.models.Specialist.Specialist;

import java.util.ArrayList;
import java.util.List;

public class SpecialistRepositoryMock implements SpecialistRepository {

    private ArrayList<Specialist> specialists;

    public SpecialistRepositoryMock() {
        List<Specialist> specialistList = List.of(
                new Specialist(0, "Kees"),
                new Specialist(0, "Henk"),
                new Specialist(0, "Piet"),
                new Specialist(0, "Judith"),
                new Specialist(0, "Bea")
        );

        this.specialists = new ArrayList<>(specialistList);
    }

    @Override
    public List<Specialist> findAll() {
        return this.specialists;
    }

    @Override
    public List<Specialist> findAllForProject(long id) {
        return this.specialists;
    }

    @Override
    public Specialist findById(long id) {
        return this.specialists.get(0);
    }

    @Override
    public Specialist insert() {
        return null;
    }

    @Override
    public Specialist update() {
        return null;
    }

    @Override
    public Specialist delete() {
        return null;
    }


}
