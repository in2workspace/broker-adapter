package es.in2.orionldinterface.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${app.openapi.dev-url}")
    private String devUrl;
    @Value("${app.openapi.test-url}")
    private String testUrl;
    @Value("${app.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI mymyOpenAPI() {

        // Defining servers
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in DEV Environment");

        Server testServer = new Server();
        testServer.setUrl(testUrl);
        testServer.setDescription("Server URL in TEST Environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in PROD Environment");

        // Defining contact info
        Contact contact = new Contact();
        contact.email("example@in2.es");
        contact.name("IN2");
        contact.url("https://in2.es");

        // defining license info
        License mitLicense = new License();
        mitLicense.setName("MIT License");
        mitLicense.setUrl("https://choosealicense.com/licenses/mit/");

        // defining application info
        Info info = new Info();
        info.setTitle("IN2 Blockchain Connector for DOME project");
        info.setVersion("0.0.1");
        info.setContact(contact);
        info.setDescription("This API exposes endpoints to manage the IN2 Blockchain Connector component.");
        info.setTermsOfService("$apiUrl/terms-of-service");
        info.setLicense(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, testServer, prodServer));
    }

}
