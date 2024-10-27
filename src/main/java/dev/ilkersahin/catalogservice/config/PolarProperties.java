package dev.ilkersahin.catalogservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "polar")
@Getter
@Setter
public class PolarProperties {
    /**
     * A message to welcome users.
     */
    private String greeting;
}
