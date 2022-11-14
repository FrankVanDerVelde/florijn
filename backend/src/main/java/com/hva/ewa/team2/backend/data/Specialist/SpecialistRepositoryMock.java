package com.hva.ewa.team2.backend.data.Specialist;

import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpecialistRepositoryMock implements SpecialistRepository {

    private final ArrayList<Specialist> specialists;

    public SpecialistRepositoryMock() {
        List<Specialist> specialistList = List.of(
                new Specialist(0, "withneyk@florijn.com", "/src/assets/avatars/avatar2.avif", "Withney", "Keulen"),
                new Specialist(1, "jant@florijn.com", "/src/assets/avatars/avatar3.avif", "Jan", "Timmermans")
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
