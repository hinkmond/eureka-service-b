package com.hinkmond.springcloud.hello;

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
class ServiceBRestController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/apply-for-mortgage/{term}", method = RequestMethod.GET)
    public String applyForMortgage(@PathVariable String term) {
        System.out.println("Getting Spring Cloud Service details for " + term);

        Double response = restTemplate.exchange("http://spring-cloud-service-a/calc-mortgage-pymt/{term}",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<Double>() {}, term).getBody();

        System.out.println("Response Received as " + response);

        return "Term: " + term + " years\n"+
                "Status: Approved\n"+
                "Mortgage Monthly Payment: " + response +"\n";
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}