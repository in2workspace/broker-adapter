package es.in2.brokeradapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BrokerAdapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrokerAdapterApplication.class, args);
	}

}
