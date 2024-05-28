package controller;

import java.lang.reflect.*;
import java.io.File;
import java.util.ArrayList;

public class ListClassesInPackage {

    public static ArrayList<String> classesName(String packageName) {
        ArrayList<String> listName = new ArrayList<String>();

        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File packageDirectory = new File(classLoader.getResource(packagePath).getFile());

        if (packageDirectory.exists() && packageDirectory.isDirectory()) {
            for (File file : packageDirectory.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".class")) {
                    String className = packageName + '.' + file.getName().substring(0, file.getName().lastIndexOf('.'));
                    listName.add(className);
                }
            }
        }

        return listName;
    }
}
