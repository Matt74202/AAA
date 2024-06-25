package util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.text.html.HTMLDocument.HTMLReader.CharacterAction;

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

    public static HashMap<String, Mapping> getMapping(Vector<String> controllers)
            throws ClassNotFoundException, DuplicateUrlException {
        HashMap<String, Mapping> temp = new HashMap<String, Mapping>();
        for (String controller : controllers) {
            Class<?> clazz = Class.forName(controller);
            List<Method> classMethods = Utilitaire.getClassMethodsWithAnnotation(clazz, Get.class);
            for (Method method : classMethods) {
                String annotationValue = method.getAnnotation(Get.class).value();
                if (temp.containsKey(annotationValue)) {
                    throw new DuplicateUrlException("Duplicate Url");
                }
                temp.put(annotationValue, new Mapping(controller, method));
            }
        }
        return temp;
    }

    public static String getSetterName (String fieldName) {
        return "set"+Character.toUpperCase(fieldName.charAt(0))+fieldName.substring(1) ; 
    }
}