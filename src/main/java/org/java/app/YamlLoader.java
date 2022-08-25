package org.java.app;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;

import java.io.File;
import java.util.Objects;

public class YamlLoader {
    @SneakyThrows
    public static <T> T load(String yamlFileName, Class<T> cls) {
        ObjectMapper objectMapper =
                new ObjectMapper(new YAMLFactory())
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.findAndRegisterModules();
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
        String path = getPathToResourcesByFileName(yamlFileName);
        return objectMapper.readValue(new File(path), cls);
    }
    @SneakyThrows
    public static String getPathToResourcesByFileName(String filename) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return Objects.requireNonNull(classLoader.getResource(filename).toURI().getPath());
    }
}
