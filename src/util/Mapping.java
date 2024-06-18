package util ;

import java.lang.reflect.Method;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public class Mapping {
    String className ; 
    String methodName ;
   
    /**
     * @param className
     * @param methodName
     */
    public Mapping(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    } 

    @Override
    public String toString(){
        return "ClassName =' "+ className +" ' MethodName =' "+ methodName ; 
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
    public String getMethodName() {
        return methodName;
    }

    /**
     * @param methodName the methodName to set
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
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
