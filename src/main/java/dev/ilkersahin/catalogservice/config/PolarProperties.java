package dev.ilkersahin.catalogservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "polar")
// Marks the class as a source for configuration properties
// starting with the prefix “polar”
@Getter
@Setter
public class PolarProperties {
    /**
     * A message to welcome users.
     */
// Field for the custom polar.greeting
// (prefix + field name) property, parsed as String
    private String greeting;
}
