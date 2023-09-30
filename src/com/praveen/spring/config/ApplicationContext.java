package com.praveen.spring.config;


import com.praveen.spring.annotation.Autowired;
import com.praveen.spring.annotation.Component;
import com.praveen.spring.annotation.ComponentScan;
import com.praveen.spring.annotation.Configuration;
import com.praveen.spring.service.ProductService;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class ApplicationContext {

    private static final Map<Class<?>, Object> map = new HashMap<>();

    public ApplicationContext(Class<AppConfig> appconfig) throws Exception {
        initializeSpringContext(appconfig);
    }

    private void initializeSpringContext(Class<AppConfig> configClass) throws Exception {
        if (configClass.isAnnotationPresent(Configuration.class)) {
            ComponentScan[] allAnnotations = configClass.getDeclaredAnnotationsByType(ComponentScan.class);
            List<String> packagesPath = Arrays.stream(allAnnotations)
                    .map(ComponentScan::value)
                    .toList();
            List<String> outputPackagePath = packagesPath.stream()
                    .map(str -> "out/production/SampleDependencyInjectionFramework/" + str.replace(".", "/"))
                    .toList();
            File[] files = findClasses(outputPackagePath);
            for (File file : files
            ) {
                for (String packge : packagesPath
                ) {
                    if (file.getPath().replace("/",".").contains(packge)) {
                        String className = packge + "." + file.getName().replace(".class", "");
                        System.out.println("className ===>"+className);
                        Class<?> loadingClass = Class.forName(className);
                        if (loadingClass.isAnnotationPresent(Component.class)) {
                            Object newInstance = loadingClass.getConstructor().newInstance();
                            map.put(loadingClass, newInstance);
                        }
                    }
                }

            }
        } else {
            System.out.println("Do not have to scan for now!");
        }
    }

    private File[] findClasses(List<String> packagesPath) {
        return packagesPath.stream()
                .map(File::new)
                .map(file -> file.listFiles((dir, name) -> name.endsWith(".class")))
                .filter(Objects::nonNull)
                .flatMap(Arrays::stream)
                .toArray(File[]::new);
    }

    public <T> T getBean(Class<T> clss) throws IllegalAccessException {
        T t = (T) map.get(clss);
        Field[] declaredFields = clss.getDeclaredFields();
        injectBean(t,declaredFields);
        return t;
    }

    private <T> void injectBean(T t, Field[] declaredFields) throws IllegalAccessException {
        for(Field field:declaredFields){
            if(field.isAnnotationPresent(Autowired.class)){
                field.setAccessible(true);
                Class<?> classType = field.getType();
                Object objectToBeInjected = map.get(classType);
                field.set(t,objectToBeInjected);
                Field[] declaredFields1 = classType.getDeclaredFields();
                injectBean(objectToBeInjected,declaredFields1);
            }
        }
    }
}
