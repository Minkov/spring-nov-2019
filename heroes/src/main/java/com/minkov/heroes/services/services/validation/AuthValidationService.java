package com.minkov.heroes.services.services.validation;

import com.minkov.heroes.services.models.auth.RegisterUserServiceModel;

public interface AuthValidationService {
    boolean isValid(RegisterUserServiceModel user);
}
