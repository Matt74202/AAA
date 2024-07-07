package mg.itu.prom16;


public class Mapping {
    String className;
    String methodName;

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Mapping(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "Mapping{className='" + className + "', methodName='" + methodName + "'}";
    }

   
}
