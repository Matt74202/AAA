// package util;

// import jakarta.servlet.http.HttpServletRequest;

// import java.lang.reflect.Field;
// import java.lang.reflect.InvocationTargetException;
// import java.lang.reflect.Method;
// import java.lang.reflect.Parameter;
// import java.util.HashMap;
// import java.util.Map;

// import annotation.ReqParam;

// public abstract class ServletUtil {
  
//     public static Map<String, String> extractParameters(HttpServletRequest request) {
//         Map<String, String> parameters = new HashMap<>();
//         request.getParameterMap().forEach((key, values) -> {
//             if (values.length > 0) {
//                 parameters.put(key, values[0]);
//             }
//         });
//         return parameters;
//     }

//     public static Object[] getMethodArguments(Method method, Map<String, String> params) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
//         Parameter[] parameters = method.getParameters();
//         Object[] arguments = new Object[parameters.length];

//         for (int i = 0; i < parameters.length; i++) {
//             Parameter parameter = parameters[i];
//             ReqParam reqParam = parameter.getAnnotation(ReqParam.class);

//             // if (reqParam == null) {
//             //     throw new IllegalArgumentException("Cannot match params without annotation in method : " + method.getName());
//             // }

//             String paramName = !reqParam.value().isEmpty() ? reqParam.value() : parameter.getName();

//             System.out.println("Name = " + paramName);
//             String paramValue = params.get(paramName);

//             if (paramValue != null) {
//                 arguments[i] = TypeConverter.convert(paramValue, parameter.getType());
//             } else {
//                 arguments[i] = getObjectInstance(params, parameter.getType(), paramValue);
//                 if (isBooleanType(parameter)) {
//                     arguments[i] = false;
//                 }
//             }
//         }
//         return arguments;
//     }


//     private static boolean isBooleanType(Parameter parameter) {
//         return parameter.getType().equals(boolean.class) || parameter.getType().equals(Boolean.class);
//     }

//     public static Object getObjectInstance (Map<String, String> paramsHashMap , Class<?> clazz , String annotationValue) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException{
//         Object instance = clazz.getConstructor().newInstance();
//         for (String key : paramsHashMap.keySet()){
//             String temp = key.split("\\.")[0];
//             if(temp.equals(annotationValue)){
//                 String fieldName = key.split("\\.")[1] ;
//                 Field field = clazz.getField(fieldName);
//                 String set = Utilitaire.getSetterName(fieldName);
//                 Method method = clazz.getMethod(set , field.getType());
//                 method.invoke(instance, TypeConverter.convert(paramsHashMap.get(key), field.getType()));
//             }
//         }
//         return instance ; 
//     }
// }
