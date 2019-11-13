package com.minkov.heroes.services.services;

import com.minkov.heroes.services.models.LoginUserServiceModel;
import com.minkov.heroes.services.models.auth.RegisterUserServiceModel;

public interface AuthService {
    void register(RegisterUserServiceModel model);

    LoginUserServiceModel login(RegisterUserServiceModel serviceModel) throws Exception;
}
