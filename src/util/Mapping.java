package util;

public class Mapping {
    String className;
    String methodeName;
    public Mapping(String className, String methodeName) {
        this.className = className;
        this.methodeName = methodeName;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getMethodeName() {
        return methodeName;
    }
    public void setMethodeName(String methodeName) {
        this.methodeName = methodeName;
    }
    
}
