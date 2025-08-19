package utils.conversion;


public abstract class TypeConverter {
    public static Object convertParameter(Class<?> parameterType, String parameterValue) {
        if(parameterValue == null){
            if(parameterType.isPrimitive()){
                if(parameterType.equals(int.class)){
                    return 0;
                } else  if(parameterType.equals(double.class)){
                    return 0.0;
                } else if(parameterType.equals(float.class)){
                    return 0.0f;
                } else  if(parameterType.equals(boolean.class)){
                    return false;
                }
            }
        }
        if(parameterType.equals(int.class) || parameterType.equals(Integer.class)){
            return Integer.parseInt(parameterValue);
        } else if(parameterType.equals(double.class) || parameterType.equals(Double.class)){
            return Double.parseDouble(parameterValue);
        } else if(parameterType.equals(float.class) || parameterType.equals(Float.class)){
            return Float.parseFloat(parameterValue);
        } else if (parameterType.equals(boolean.class) || parameterType.equals(Boolean.class)){
            return Boolean.parseBoolean(parameterValue);
        } else if(parameterType.equals(long.class) || parameterType.equals(Long.class)){
            return Long.parseLong(parameterValue);
        }else {
            return parameterValue;
        }
    }
}
