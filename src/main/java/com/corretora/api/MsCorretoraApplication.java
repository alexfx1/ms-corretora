package com.corretora.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@Slf4j
@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class MsCorretoraApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext app = new SpringApplicationBuilder(MsCorretoraApplication.class).run();
		Environment env = app.getEnvironment();
		String profileActive = env.getProperty("spring.profiles.active");
		String contextStr = env.getProperty("server.servlet.context-path");
		String applicationName = env.getProperty("spring.application.name");
		String applicationPort = env.getProperty("server.port");
		log.info(
				"""

						----------------------------------------------------------
						\tApplication '{}' is running!
						\tAccess URL: http://localhost:{}{}/swagger-ui.html
						----------------------------------------------------------
						\tPerfil: {}
						----------------------------------------------------------""",
				applicationName, applicationPort , contextStr, profileActive
		);
	}

}
