package es.in2.orionld.config;

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

    @Bean
    public OpenAPI mymyOpenAPI() {
        // Defining servers
        Server devServer = getServer();
        // Defining contact info
        Contact contact = getContact();
        // defining license info
        License mitLicense = getLicense();
        // defining application info
        Info info = getInfo(contact, mitLicense);
        return new OpenAPI().info(info).servers(List.of(devServer));
    }

    private Server getServer() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in DEV Environment");
        return devServer;
    }

    private static Info getInfo(Contact contact, License mitLicense) {
        Info info = new Info();
        info.setTitle("IN2 Blockchain Connector for DOME project");
        info.setVersion("0.0.1");
        info.setContact(contact);
        info.setDescription("This API exposes endpoints to manage the IN2 Blockchain Connector component.");
        info.setTermsOfService("$apiUrl/terms-of-service");
        info.setLicense(mitLicense);
        return info;
    }

    private static License getLicense() {
        License mitLicense = new License();
        mitLicense.setName("MIT License");
        mitLicense.setUrl("https://choosealicense.com/licenses/mit/");
        return mitLicense;
    }

    private static Contact getContact() {
        Contact contact = new Contact();
        contact.email("example@in2.es");
        contact.name("IN2");
        contact.url("https://in2.es");
        return contact;
    }

}
