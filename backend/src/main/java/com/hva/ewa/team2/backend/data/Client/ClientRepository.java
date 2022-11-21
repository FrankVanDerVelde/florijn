package com.hva.ewa.team2.backend.data.Client;


import java.util.List;

import com.hva.ewa.team2.backend.domain.models.user.Client;

public interface ClientRepository {

    List<Client> findAll();

    Client findById(long id);

    Client insert();

    Client update();

    Client delete();

}
