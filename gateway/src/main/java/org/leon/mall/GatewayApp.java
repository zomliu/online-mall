package org.leon.mall;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApp {
    Logger logger = Logger.getLogger(GatewayApp.class.getName());
    public static void main( String[] args ) {
        SpringApplication.run(GatewayApp.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(DiscoveryClient discoveryClient) {
        return args -> {
            List<String> services = discoveryClient.getServices();
            logger.info("Registered Services: " + services);

            for (String service : services) {
                List<ServiceInstance> instances = discoveryClient.getInstances(service);
                logger.info("Instances of " + service + ": " + instances);

                instances.forEach(serviceInstance -> {
                    logger.info("Instance: " + serviceInstance.getHost() + ":" + serviceInstance.getPort());
                });
            }
        };
    }

    @Bean
    WebFilter loggingFilter() {
        return (ServerWebExchange exchange, org.springframework.web.server.WebFilterChain chain) -> {
            logger.info("Request URI: " + exchange.getRequest().getURI());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("Request URI: " + exchange.getResponse().getStatusCode().value());
            }));
        };
    }
}
