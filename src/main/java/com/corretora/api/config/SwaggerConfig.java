package com.corretora.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

@Configuration
public class SwaggerConfig {
    @Value("${git.api.url}")
    private String git;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.profiles.active:local}")
    private String profile;

    @Value("${server.servlet.context-path}")
    private String servletContextPath;

    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(Collections.singletonList(new Server().url(servletContextPath)));
    }

    public Info apiInfo() {

        License licence = new License()
                .name("Repositório GIT - "+ git)
                .url(git);
        return new Info().title(applicationName)
                .description(getApiDescription())
                .version("")
                .license(licence);
    }

    private String getApiDescription() {
        String quebraLinha = "<br>";
        StringBuilder sb = new StringBuilder();
        String hostName = "";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostName = " #desconhecido# ";
        }
        String dataCompilacao = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        String applicationDescription = "Api savastano corretora.";
        sb.append(applicationDescription).append(quebraLinha);
        sb.append("<b>Profile:</b> ").append(profile);
        sb.append(" | <b><i>Compilado em: </b></i>").append(dataCompilacao);
        sb.append(" | <b>Host:</b> ").append(hostName);
        return sb.toString();
    }
}
