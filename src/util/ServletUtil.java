package util;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import annotation.ReqParam;

public abstract class ServletUtil {
  
    public static Map<String, String> extractParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        request.getParameterMap().forEach((key, values) -> {
            if (values.length > 0) {
                parameters.put(key, values[0]);
            }
        });
        return parameters;
    }

    public static Object[] getMethodArguments(Method method, Map<String, String> params) {
        Parameter[] parameters = method.getParameters();
        Object[] arguments = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            ReqParam reqParam = parameter.getAnnotation(ReqParam.class);

            if (reqParam == null) {
                throw new IllegalArgumentException("Cannot match params without annotation in method : " + method.getName());
            }

            String paramName = !reqParam.value().isEmpty() ? reqParam.value() : parameter.getName();

            System.out.println("Name = " + paramName);
            String paramValue = params.get(paramName);

            if (paramValue != null) {
                arguments[i] = TypeConverter.convert(paramValue, parameter.getType());
            } else {
                arguments[i] = null;
                if (isBooleanType(parameter)) {
                    arguments[i] = false;
                }
            }
        }
        return arguments;
    }


    private static boolean isBooleanType(Parameter parameter) {
        return parameter.getType().equals(boolean.class) || parameter.getType().equals(Boolean.class);
    }
}
