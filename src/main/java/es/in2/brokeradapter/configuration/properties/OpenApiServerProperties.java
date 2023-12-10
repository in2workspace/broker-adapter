package es.in2.brokeradapter.configuration.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import static es.in2.brokeradapter.utils.HttpUtils.isNullOrBlank;

@Slf4j
public record OpenApiServerProperties(String url, String description) {

    @ConstructorBinding
    public OpenApiServerProperties(String url, String description) {
        this.url = isNullOrBlank(url) ? "https://localhost:8080" : url;
        this.description = isNullOrBlank(description) ? "{server description}" : description;
    }

}
