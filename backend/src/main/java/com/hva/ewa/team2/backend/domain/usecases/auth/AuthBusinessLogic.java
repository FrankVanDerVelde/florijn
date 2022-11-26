package com.hva.ewa.team2.backend.domain.usecases.auth;

import com.hva.ewa.team2.backend.rest.user.json.JsonUserInfo;

public interface AuthBusinessLogic {
    JsonUserInfo getUserInfoByCredentials(String email, String password);
}
