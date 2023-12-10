package es.in2.brokeradapter;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BrokerAdapterApplication {

    private static final ObjectMapper OBJECT_MAPPER =
            // sort alphabetically, to ensure same order when hashing.
            JsonMapper.builder().configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true).build();

    public static void main(String[] args) {
        SpringApplication.run(BrokerAdapterApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        // the ObjectMapper should only be created once per JVM for resource reasons. To ensure thread-safety, get
        // the thread-safe reader and writer instances from it.
        return OBJECT_MAPPER;
    }

}
