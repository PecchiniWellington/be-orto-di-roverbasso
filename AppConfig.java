import io.github.cdimascio.dotenv.Dotenv;

public class AppConfig {
    public static void main(String[] args) {
        String environment = System.getenv("APP_ENV");
        if (environment == null) {
            environment = "development";
        }

        Dotenv dotenv;
        if ("production".equals(environment)) {
            dotenv = Dotenv.configure().filename(".env.production").load();
        } else if ("test".equals(environment)) {
            dotenv = Dotenv.configure().filename(".env.test").load();
        } else {
            dotenv = Dotenv.configure().filename(".env.development").load();
        }

        String dbUrl = dotenv.get("DB_URL");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");
        String dbDriver = dotenv.get("DB_DRIVER");
        String dbSslMode = dotenv.get("DB_SSL_MODE");

        System.out.println("Connecting to database at: " + dbUrl);
        System.out.println("DB_USER: " + dbUser);
        System.out.println("DB_PASSWORD: " + dbPassword);
        System.out.println("DB_DRIVER: " + dbDriver);
        System.out.println("SSL Mode: " + dbSslMode);

    }
}
