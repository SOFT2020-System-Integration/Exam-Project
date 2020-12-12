package app.shipping;

import app.shipping.controllers.ShippingCamundaController;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.logging.Level;
import java.util.logging.Logger;

@EnableDiscoveryClient
@SpringBootApplication
public class ShippingCamundaApplication implements CommandLineRunner {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(ShippingCamundaController.class));

    public static void main(String[] args) {
        SpringApplication.run(ShippingCamundaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(10000) // long polling timeout
                .build();

        // subscribe to an external task topic as specified in the process
        client.subscribe("payment-checker")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here
                    LOGGER.log(Level.INFO,"[LOGGER] ::: CALLING CLIENT PAYMENT CHECKER");
                    // Get a process variable
                    String game = (String) externalTask.getVariable("game");
                    String type = (String) externalTask.getVariable("type");
                    //boolean shipped = (boolean) externalTask.getVariable("shipped");

                    if (type.equals("PHYSICAL")) {
                        LOGGER.log(Level.INFO, "[LOGGER] ::: PHYSICAL ORDER SENT ::: " + game);
                    } else {
                        LOGGER.log(Level.INFO, "[LOGGER] ::: DIGITAL ORDER READY FOR DOWNLOAD ::: " + game);
                    }
                    // Complete the task
                    externalTaskService.complete(externalTask);
                })
                .open();
        LOGGER.log(Level.INFO, "[LOGGER] ::: LISTENING FOR MESSAGES");
    }

}


