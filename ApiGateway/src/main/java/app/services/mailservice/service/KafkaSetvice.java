package app.services.mailservice.service;

import app.controllers.rest.OrderMongoController;
import app.models.order.Order;
import app.models.order.OrderLine;
import app.models.order.Status;
import app.repositories.MongoClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class KafkaSetvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSetvice.class);
    private final List<String> messages = new ArrayList<>();
    private final MailService mailservice = new MailService();

    private final OrderMongoController controller;
    public KafkaSetvice(OrderMongoController controller, MongoClient client){
        this.controller = controller;
    }

    @KafkaListener(topics = "order-broker", groupId = "my-group")
    public void listenToMessages(String message) throws IOException {
        synchronized (messages) {
            messages.add(message);
            JsonObject convertedObject = new Gson().fromJson(message, JsonObject.class);
            String orderLineId = convertedObject.get("orderlineId").getAsString();
            String orderId = convertedObject.get("orderId").getAsString();

            controller.updateOrderLineStatusByOrderAndOrderLineId(orderId, orderLineId, Status.COMPLETED);
            Order order = controller.retrieveOrderById(orderId);
            boolean inProgress = false;
            for(OrderLine ol : order.getOrderLines())
            {
                if(ol.getStatus() == Status.IN_PROGRESS)
                {
                    inProgress = true;
                }
            }
            if(!inProgress)
            {
                controller.updateOrderStatus(orderId, Status.COMPLETED);
            }
            mailservice.sendOrderConfirmationEmail(order);
        }

        LOGGER.info("&&& Message Consumed: [" + message + "]");
    }

    public List<String> getMessages() {
        return messages;
    }

}
