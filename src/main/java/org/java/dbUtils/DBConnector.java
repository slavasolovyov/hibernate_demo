package org.java.dbUtils;

import lombok.SneakyThrows;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.java.app.ConfigurationApp;

import javax.persistence.Entity;
import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DBConnector {
    private static SessionFactory sessionFactory;

    static {
        try {
            ConfigurationApp.Config config = ConfigurationApp.getConfig();
            System.out.println("url " + config.getDb().getUrl());
            System.out.println("username " + config.getDb().getUser());
            System.out.println("password " + config.getDb().getPassword());
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.url", config.getDb().getUrl())
                    .setProperty("hibernate.connection.username", config.getDb().getUser())
                    .setProperty("hibernate.connection.password", config.getDb().getPassword())
                    .configure();
            getEntityClassesFromPackage("org.java.dto").forEach(configuration::addAnnotatedClass);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private static List<Class<?>> getEntityClassesFromPackage(String packageName){
        List<String> classNames = getClassNamesFromPackage(packageName);
        List<Class<?>> classes = new ArrayList<>();
        for (String className : classNames) {
            Class<?> cls = Class.forName(packageName + "." + className);
            Annotation[] annotations = cls.getAnnotations();

            for (Annotation annotation : annotations) {
                System.out.println(cls.getCanonicalName() + ": " + annotation.toString());
                if (annotation instanceof Entity) {
                    classes.add(cls);
                }
            }
        }
        return classes;
    }

    @SneakyThrows
    private static ArrayList<String> getClassNamesFromPackage(String packageName){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ArrayList<String> names = new ArrayList<String>();

        packageName = packageName.replace(".", "/");
        URL uri = classLoader.getResource(packageName);
        File folder = new File(Objects.requireNonNull(uri).getPath());
        File[] files = folder.listFiles();
        for (File file: Objects.requireNonNull(files)) {
            String name = file.getName();
            name = name.substring(0, name.lastIndexOf('.'));  // remove ".class"
            names.add(name);
        }
        return names;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
