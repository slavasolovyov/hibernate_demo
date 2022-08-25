package org.java.app;

import lombok.Getter;

public class ConfigurationApp {
    private static Config config = YamlLoader.load("app.yaml", Config.class);

    public static Config getConfig() {
        return config;
    }

    @Getter
    public static class Config {
        private DBConfig db;
    }

    @Getter
    public static class DBConfig {
        String url;
        String user;
        String password;

        @Override
        public String toString() {
            return "DBConfig{" +
                    "url='" + url + '\'' +
                    ", user='" + user + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
