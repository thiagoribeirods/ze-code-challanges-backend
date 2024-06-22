package ze.code.challenge.app;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ze.code.challenge.app.entity.Address;
import ze.code.challenge.app.entity.Area;
import ze.code.challenge.app.entity.Partner;

@SpringBootApplication
public class ZeCodeChallengesBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeCodeChallengesBackendApplication.class, args);
    }

}
