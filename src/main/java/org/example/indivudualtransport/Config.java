package org.example.indivudualtransport;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Tobias Heidlund
 */

@ConfigurationProperties(prefix = "config")
@Data
public class Config {
    private String message;

}
