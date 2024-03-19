package dev.alexandreoliveira.gft.aodev.travels.flights.core.utils.validators;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ModelValidatorUtil {

    public static <Model> List<String> isValid(Model model, Class<?> ...groups) {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Model>> violations = validator.validate(model, groups);

            if (violations.isEmpty()) {
                return Collections.emptyList();
            }

            List<String> errors = new ArrayList<>();
            violations.forEach(error -> {
                String errorMessage = String.format("%s: %s (%s)",
                        error.getPropertyPath().toString(),
                        error.getMessage(),
                        error.getInvalidValue());
                errors.add(errorMessage);
            });
            return errors;
        } catch (Exception e) {
            throw e;
        }
    }
}
