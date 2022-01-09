package org.aiolabs.projects.converter.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.aiolabs.projects.converter.beans.Profile;
import org.aiolabs.projects.converter.services.contracts.BeanValidator;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;

@Component
@Slf4j
public class ProfileBeanValidator implements BeanValidator<Profile> {

    private final Validator validator;

    public ProfileBeanValidator() {
        validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
    }

    @Override
    public boolean validate(Profile data) {
        Set<ConstraintViolation<Profile>> validationStatus = validator.validate(data);
        if (Objects.nonNull(validationStatus) && validationStatus.size() > 0) {
            validationStatus.forEach(it -> log.warn(it.getMessage()));
            return false;
        }
        return true;
    }
}
