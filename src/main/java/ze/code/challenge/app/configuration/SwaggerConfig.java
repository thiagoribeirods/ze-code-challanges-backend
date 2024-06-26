package ze.code.challenge.app.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI api() {
        Info info = new Info()
                .title("Ze Code Challenge Documentation")
                .version("1.0.0")
                .description("This is an implementation from " +
                        "<a href='https://github.com/ab-inbev-ze-company/ze-code-challenges/blob/master/backend.md' target='_blank'>" +
                            "https://github.com/ab-inbev-ze-company/ze-code-challenges/blob/master/backend.md" +
                        "</a>");

        return new OpenAPI().info(info);
    }

}
