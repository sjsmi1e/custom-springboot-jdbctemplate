package com.smile;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author smi1e
 * Date 2019/10/15 16:38
 * Description
 */
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class JdbcPropertise {
    private String driver;
    private String url;
    private String username;
    private String password;
}
