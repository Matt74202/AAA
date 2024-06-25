package util ;

import java.lang.reflect.Method;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public class Mapping {
    String className ; 
    Method method;
   
    /**
     * @param className
     * @param methodName
     */
    public Mapping(String className, Method methodName) {
        this.className = className;
        this.method = methodName;
    } 

    @Override
    public String toString(){
        return "ClassName =' "+ className +" ' MethodName =' "+ method ; 
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the methodName
     */
    public Method getMethod() {
        return method;
    }

    /**
     * @param methodName the methodName to set
     */
    public void setMethod(Method methodName) {
        this.method = methodName;
    }

    public Object invoke(HttpServletRequest request , Class<?> clazz , Method method  ) throws ServletException {
        try {
            Object obj = clazz.getDeclaredConstructor().newInstance();
            Map<String, String> params = ServletUtil.extractParameters(request);
            Object[] args = ServletUtil.getMethodArguments(method, params);

            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new ServletException("Error invoking method", e);
        }
    }
}
