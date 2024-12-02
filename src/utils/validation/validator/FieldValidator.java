package utils.validation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import exception.ModelValidationException;

public interface FieldValidator {
    public void validate(String value, Annotation annotation,Field field) throws ModelValidationException;
}
