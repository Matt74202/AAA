package utils.validation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import exception.ModelValidationException;

public class NumericValidator implements FieldValidator{

    @Override
    public void validate(String value, Annotation annotation, Field field) throws ModelValidationException {
        if (!(value.matches("\\d+"))) {
            throw new ModelValidationException(field.getName() + " should be numeric");
        }
    }
    
}
