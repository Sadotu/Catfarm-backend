package Validators;

import team.catfarm.DTO.Output.EventOutputDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TimeOrderValidator implements ConstraintValidator<ValidTimeOrder, EventOutputDTO> {

    @Override
    public void initialize(ValidTimeOrder constraintAnnotation) {
        // Initialization code, if needed
    }

    @Override
    public boolean isValid(EventOutputDTO event, ConstraintValidatorContext context) {
        if (event.getStartTime() == null || event.getEndTime() == null) {
            return true; // validation for nulls should be done using @NotNull annotation
        }
        return event.getEndTime().isAfter(event.getStartTime());
    }
}
