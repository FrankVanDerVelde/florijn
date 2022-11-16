package com.hva.ewa.team2.backend.data.specialist;

import com.hva.ewa.team2.backend.domain.models.user.Specialist;

import java.util.List;

public interface SpecialistRepository {

    List<Specialist> findAll();

    List<Specialist> findAllForProject(long id);

    Specialist findById(long id);

    Specialist insert();

    Specialist update();

    Specialist delete();
}
