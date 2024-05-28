package util;

import java.io.File;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Vector;

import annotation.Get;

public class Utilitaire {
    // public String modifyPath (String path) {
    //     path= path.substring(1);
    //     path= path.replace("%20", " ");
    //     return path;
    // }

    // public Vector<String> getListController (String packageName, Class<? extends Annotation> annotation) throws ClassNotFoundException {
    //     Vector<String> controllers = new Vector<String>();
    //     packageName.replace(".", "/");
    //     try{
    //         ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    //         java.net.URL source= classLoader.getResource(packageName);

    //         String realPath= this.modifyPath(source.getFile());

    //         File classPathDirectory = new File(realPath);
    //         if (classPathDirectory.isDirectory()){
    //             packageName= packageName.replace("/", ".");
    //             for (String fileName: classPathDirectory.list()){
    //                 fileName= fileName.substring(0, fileName.length()-6);
    //                 String className = packageName+"."+fileName;
    //                 Class<?> clazz= Class.forName(className);
    //                 if (clazz.isAnnotationPresent(annotation)){
    //                     controllers.add(className);
    //                 }
    //             }
    //         }
    //     }
    //     catch (ClassNotFoundException e){
    //         System.out.println(e.getMessage());
    //     }
    //     return controllers;
    // }

    public static HashMap getAllClassesSelonAnnotation(String packageToScan,Class<?>annotation) throws Exception{
        //List<String> controllerNames = new ArrayList<>();
        HashMap<String,Mapping> hm=new HashMap<>();
        try {
            
            //String path = getClass().getClassLoader().getResource(packageToScan.replace('.', '/')).getPath();
            String path = Thread.currentThread().getContextClassLoader().getResource(packageToScan.replace('.', '/')).getPath();
            String decodedPath = URLDecoder.decode(path, "UTF-8");
            File packageDir = new File(decodedPath);

            File[] files = packageDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        String className = packageToScan + "." + file.getName().replace(".class", "");
                        Class<?> clazz = Class.forName(className);
                        if (clazz.isAnnotationPresent(annotation.asSubclass(java.lang.annotation.Annotation.class))) {
                            //controllerNames.add(clazz.getSimpleName());
                            Method[]methods=clazz.getDeclaredMethods();
                            for (Method m : methods) {
                                if (m.isAnnotationPresent(Get.class)) {
                                    Get getAnnotation= m.getAnnotation(Get.class);
                                    hm.put(getAnnotation.url(),new Mapping(clazz.getSimpleName(),m.getName()));
                                }
                            }
                        }
                    }
                }
            }
           
        } catch (Exception e) {
            throw e;
        }
        return hm;
    }
}
