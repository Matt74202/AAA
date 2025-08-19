package utils.validation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import annotation.validation.DateType;
import annotation.validation.Email;
import annotation.validation.Length;
import annotation.validation.Numeric;
import annotation.validation.Required;
import utils.validation.validator.DateValidator;
import utils.validation.validator.EmailValidator;
import utils.validation.validator.FieldValidator;
import utils.validation.validator.LengthValidator;
import utils.validation.validator.NeutralValidator;
import utils.validation.validator.NumericValidator;
import utils.validation.validator.RequiredValidator;

public class ValidatorRegistry {

    private ValidatorRegistry() {
    }

    private static final Map<Class<? extends Annotation>, FieldValidator> validators = new HashMap<>();
    
    static {
        validators.put(Length.class, new LengthValidator());
        validators.put(DateType.class, new DateValidator());
        validators.put(Email.class, new EmailValidator());
        validators.put(Required.class, new RequiredValidator());
        validators.put(Numeric.class, new NumericValidator());
    }
    
    public static FieldValidator getValidator(Class<? extends Annotation> annotation) {
        FieldValidator validator = validators.get(annotation);
        if (validator == null) {
            validator = new NeutralValidator();
        }
        return validator;
    }
}
