package org.leon.mall;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//@EnableDiscoveryClient  高版本也可以不写，有依赖和配置会自动去注册
@EnableFeignClients
public class OrderApp {
    public static void main( String[] args ) {
        SpringApplication.run(OrderApp.class, args);
    }

    // 使用了 OpenFeign 就不需要直接调用 @LoadBalance RestTemplate 了
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
