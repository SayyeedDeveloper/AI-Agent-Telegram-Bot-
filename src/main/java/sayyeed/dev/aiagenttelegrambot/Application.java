package sayyeed.dev.aiagenttelegrambot;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    static {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        dotenv.entries().forEach(e -> {
            System.setProperty(e.getKey(), e.getValue());
        });
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
