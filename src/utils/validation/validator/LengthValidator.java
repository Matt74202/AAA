package utils.validation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import annotation.validation.Length;
import exception.ModelValidationException;

public class LengthValidator implements FieldValidator {

    @Override
    public void validate(String value, Annotation annotation, Field field) throws ModelValidationException {
        int minLength = ((Length) annotation).length();
        if ((value).length() < minLength) {
            throw new ModelValidationException(field.getName() + " must have at least " + minLength + " characters.");
        }
    }

}
