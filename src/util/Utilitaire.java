package util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Vector;

public class Utilitaire {
    public String modifyPath (String path) {
        path= path.substring(1);
        path= path.replace("%20", " ");
        return path;
    }

    public Vector<String> getListController (String packageName, Class<? extends Annotation> annotation) throws ClassNotFoundException {
        Vector<String> controllers = new Vector<String>();
        packageName.replace(".", "/");
        try{
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            java.net.URL source= classLoader.getResource(packageName);

            String realPath= this.modifyPath(source.getFile());

            File classPathDirectory = new File(realPath);
            if (classPathDirectory.isDirectory()){
                packageName= packageName.replace("/", ".");
                for (String fileName: classPathDirectory.list()){
                    fileName= fileName.substring(0, fileName.length()-6);
                    String className = packageName+"."+fileName;
                    Class<?> clazz= Class.forName(className);
                    if (clazz.isAnnotationPresent(annotation)){
                        controllers.add(className);
                    }
                }
            }
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return controllers;
    }
}
