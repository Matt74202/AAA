package util ;

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
}
