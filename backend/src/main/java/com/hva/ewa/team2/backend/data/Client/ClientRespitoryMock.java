package com.hva.ewa.team2.backend.data.Client;

import com.hva.ewa.team2.backend.domain.models.user.Client;

import java.util.List;

public class ClientRespitoryMock implements ClientRepository {


    List<Client> clients = List.of(
            new Client(1, "ingbankieren@outlook.com", null, "ING Bankieren", null),
            new Client(1, "rabobank@outlook.com", null, "Rabobank Bankieren", null)
    );


    @Override
    public List<Client> findAll() {
        return clients;
    }

    @Override
    public Client findById(long id) {
        if (id > 0 && id < this.clients.size()) {
            return this.clients.get((int) id);
        } else {
            return null;
        }
    }

    @Override
    public Client insert() {
        return null;
    }

    @Override
    public Client update() {
        return null;
    }

    @Override
    public Client delete() {
        return null;
    }
}
