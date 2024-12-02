package utils.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import exception.ModelValidationException;
import utils.validation.validator.FieldValidator;

public class Validator {
    public static void checkField(String value, Field field) throws ModelValidationException {
        Annotation[] annotations = field.getAnnotations();

        for(Annotation annotation : annotations) {
            FieldValidator validator = ValidatorRegistry.getValidator(annotation.annotationType());

            if (validator != null) {
                validator.validate(value , annotation, field);
            }
        }
    }
}
