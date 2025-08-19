package utils.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import annotation.ReqParam;
import exception.ModelValidationException;
import jakarta.servlet.http.HttpServletRequest;
import manager.data.FieldExceptions;
import manager.data.ModelValidationExceptionHandler;
import utils.ObjectUtils;
import utils.validation.validator.FieldValidator;

public class Validator {

    private Validator() {}


    public static void checkField(String value, Field field) throws ModelValidationException {
        Annotation[] annotations = field.getAnnotations();

        for(Annotation annotation : annotations) {
            FieldValidator validator = ValidatorRegistry.getValidator(annotation.annotationType());

            if (validator != null) {
                validator.validate(value , annotation, field);
            }
        }
    }

     public static FieldExceptions getModelFieldExceptions(String value, Field field) {
        List<Exception> exceptions = new ArrayList<>();
        FieldExceptions fieldExceptions = new FieldExceptions(exceptions, value);

        Annotation[] annotations = field.getDeclaredAnnotations();

        for(Annotation annotation : annotations) {
            FieldValidator validator = ValidatorRegistry.getValidator(annotation.annotationType());

            if (validator != null) {
                try {
                    validator.validate(value , annotation, field);
                } catch (Exception e) {
                    fieldExceptions.addException(e);
                }
            }
        }

        return fieldExceptions;
    }

    public static void validateModel(Class<?> modelType, String identifier, ModelValidationExceptionHandler handler, HttpServletRequest request) {
        Field[] fields = modelType.getDeclaredFields();

        for (Field field : fields) {
            String name = identifier + "." + field.getName();
            FieldExceptions temp = getModelFieldExceptions(request.getParameter(name), field);
            handler.addFieldException(name, temp);
        }
    }

    public static ModelValidationExceptionHandler validateMethod(Method method, HttpServletRequest request) {
        ModelValidationExceptionHandler handler = new ModelValidationExceptionHandler();

        Parameter[] parameters = method.getParameters();

        for(Parameter parameter : parameters) {
            if (ObjectUtils.isClassModel(parameter.getType())) {
                String identifier = parameter.getAnnotation(ReqParam.class).value();
                validateModel(parameter.getType() , identifier, handler, request);
            }
        }

        return handler;
    }
}
