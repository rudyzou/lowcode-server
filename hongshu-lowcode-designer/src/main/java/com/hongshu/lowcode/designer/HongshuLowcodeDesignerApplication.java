package com.hongshu.lowcode.designer;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.spring.EnableSpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@SpringBootApplication(scanBasePackages = "com.hongshu")
@EnableJpaRepositories(basePackages = "com.hongshu")
@EnableSpringUtil
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EntityScan(basePackages = {"com.hongshu.jpa.domain",
		"com.hongshu.comnsvc.base.domain",
		"com.hongshu.lowcode.designer.domain"})
public class HongshuLowcodeDesignerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HongshuLowcodeDesignerApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> {
			return Optional.ofNullable(StpUtil.getLoginId().toString());
		};
	}
}
