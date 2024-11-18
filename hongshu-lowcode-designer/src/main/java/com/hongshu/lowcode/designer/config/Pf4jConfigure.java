package com.hongshu.lowcode.designer.config;

import com.hongshu.pf4j.spring.SpringPluginManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class Pf4jConfigure {
    @Bean
    public SpringPluginManager pluginManager() {
        return new SpringPluginManager();
    }
}
