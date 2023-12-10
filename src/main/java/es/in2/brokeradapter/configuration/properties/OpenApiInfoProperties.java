package es.in2.brokeradapter.configuration.properties;

import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

import static es.in2.brokeradapter.utils.HttpUtils.isNullOrBlank;

public record OpenApiInfoProperties(String title, String version, String description, String termsOfService,
                                    @NestedConfigurationProperty OpenApiInfoContactProperties contact,
                                    @NestedConfigurationProperty OpenApiInfoLicenseProperties license) {

    @ConstructorBinding
    public OpenApiInfoProperties(String title, String version, String description, String termsOfService,
                                 OpenApiInfoContactProperties contact, OpenApiInfoLicenseProperties license) {
        this.title = isNullOrBlank(title) ? "{name of your company}" : title;
        this.version = isNullOrBlank(version) ? "1.0.0-SNAPSHOT" : version;
        this.description = isNullOrBlank(description) ? "{description of your company}" : description;
        this.termsOfService = isNullOrBlank(termsOfService) ? "https://www.example.com/terms-of-service" : termsOfService;
        this.contact = Optional.ofNullable(contact).orElse(new OpenApiInfoContactProperties(null, null, null));
        this.license = Optional.ofNullable(license).orElse(new OpenApiInfoLicenseProperties(null, null));
    }

}
