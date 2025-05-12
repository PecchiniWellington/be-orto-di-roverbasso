package com.ortoroverbasso.ortorovebasso.utils;

public class EnvUtil {
    public static boolean isProduction() {
        String env = System.getenv("ENV");
        return env != null && !env.equalsIgnoreCase("local") && !env.equalsIgnoreCase("dev");
    }
}
