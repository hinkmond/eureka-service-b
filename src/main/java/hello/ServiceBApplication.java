package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class ServiceBApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceBApplication.class, args);
    }
}

@RestController
class ListServiceController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/list-service/{appName}", method = RequestMethod.GET)
    public String getStudents(@PathVariable String appName) {
        System.out.println("Getting Spring Cloud Service details for " + appName);

        String response = restTemplate.exchange("http://spring-cloud-service-a/fetch-service-instances/{appName}",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {}, appName).getBody();

        System.out.println("Response Received as " + response);

        return "Service Name -  " + appName + " \n Service Details " + response;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}