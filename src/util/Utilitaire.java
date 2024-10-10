package util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import annotation.Get;

import exception.*;

public class Utilitaire {
    public static String modifyPath(String path) {
        path = path.substring(1);
        path = path.replace("%20", " ");
        return path;
    }

    public static Vector<String> getListController(String packageName, Class<? extends Annotation> annotation)
            throws ClassNotFoundException {
        Vector<String> controllers = new Vector<String>();
        packageName.replace(".", "/");
        try {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            java.net.URL source = classLoader.getResource(packageName);

            String realPath = modifyPath(source.getFile());

            File classPathDirectory = new File(realPath);
            if (classPathDirectory.isDirectory()) {
                packageName = packageName.replace("/", ".");
                for (String fileName : classPathDirectory.list()) {
                    fileName = fileName.substring(0, fileName.length() - 6);
                    String className = packageName + "." + fileName;
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(annotation)) {
                        controllers.add(className);
                    }
                }
            }
        } catch (Exception e) {

        }
        return controllers;

    }

    public static List<Method> getClassMethodsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        List<Method> methods = new ArrayList<>();
        Method[] classMethods = clazz.getDeclaredMethods();

        for (Method method : classMethods) {
            if (method.isAnnotationPresent(annotation)) {
                methods.add(method);
            }
        }

        return methods;
    }

    // public static HashMap<String, Mapping> getMapping(Vector<String> controllers)
    //         throws ClassNotFoundException, DuplicateUrlException {
    //     HashMap<String, Mapping> temp = new HashMap<String, Mapping>();
    //     for (String controller : controllers) {
    //         Class<?> clazz = Class.forName(controller);
    //         List<Method> classMethods = Utilitaire.getClassMethodsWithAnnotation(clazz, Get.class);
    //         for (Method method : classMethods) {
    //             String annotationValue = method.getAnnotation(Get.class).value();
    //             if (temp.containsKey(annotationValue)) {
    //                 throw new DuplicateUrlException("Duplicate Url");
    //             }
    //             temp.put(annotationValue, new Mapping(controller, method));
    //         }
    //     }
    //     return temp;
    // }

    public  String getAnnotatedClassWithin(String packagename, Class<? extends Annotation> annotationClass) {
        String ListService = "";
        packagename = packagename.replace(".", "/");

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource(packagename);

            if (resource == null) {
                System.out.println("Package not found: " + packagename);
                return "";
            }

            String filepath = resource.getFile().replace("%20", " ");
            File directory = new File(filepath);

            if (directory.isDirectory()) {
                packagename = packagename.replace("/", ".");

                for (String filename : directory.list()) {
                    if (filename.endsWith(".class")) {
                        filename = filename.substring(0, filename.length() - 6);
                        String className = packagename + "." + filename;
                        System.out.println("Found class: " + className);
                        Class<?> clazz = Class.forName(className);
                        if (clazz.isAnnotationPresent(annotationClass)) {
                            System.out.println("Annotated class found: " + className);
                            ListService += className + ",";
                        }
                    }
                }
            } else {
                System.out.println("Not a directory: " + filepath);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
        }
        return ListService;
    }

    public  boolean wasUsed(String url, HashMap<String, Mapping> ListClasses) {
        return ListClasses.containsKey(url);
    }

    public  HashMap<String, Mapping> getMapping(String packagename, Class<? extends Annotation> annotationClass) throws DuplicateUrlException {
        String[] ListController = getAnnotatedClassWithin(packagename, annotationClass).split(",");
        HashMap<String, Mapping> ListClasses = new HashMap<>();
        for (String className : ListController) {
            if (className.isEmpty()) {
                continue;
            }
            try {
                Class<?> clazz = Class.forName(className);
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Get.class)) {
                        Mapping value = new Mapping(className, method.getName());
                        String key = method.getAnnotation(Get.class).value();
                        if (!wasUsed(key, ListClasses)) {
                            ListClasses.put(key, value);
                            System.out.println("Mapped URL: " + key + " to method: " + method.getName());
                        } else {
                            throw new DuplicateUrlException("The url: " + key + " from " + className + " method " + method.getName() + " is already used by another class!");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error processing class: " + className + " - " + e.getMessage());
            }
        }
        return ListClasses;
    }

    public static String getSetterName (String fieldName) {
        return "set"+Character.toUpperCase(fieldName.charAt(0))+fieldName.substring(1) ; 
    }

    public String conform_url(String url) {
        String newURL = "/";
        String[] path1 = url.split("//");
        String[] path = path1[1].split("/");
        for (int i = 2; i < path.length; i++) {
            newURL += path[i] + "/";
        }
        return newURL.substring(0, newURL.length() - 1);
    }
}