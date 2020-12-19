package app;

import app.controllers.rest.OrderMongoController;
import app.models.order.Order;
import app.services.mailservice.service.MailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Collection;
import java.util.List;

@EnableHystrixDashboard
@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
// Feign enables dynamic processing of annotations
public class GatewayApplication implements CommandLineRunner {
    final OrderMongoController controller;

    public GatewayApplication(OrderMongoController controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {SpringApplication.run(GatewayApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        MailService ms = new MailService();
        Collection<Order> o = controller.retrieveAllOrders();

        ms.sendOrderConfirmationEmail(o.iterator().next());
    }
}



