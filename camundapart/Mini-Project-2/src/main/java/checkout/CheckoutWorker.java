package checkout;

import java.util.logging.Logger;
import org.camunda.bpm.client.ExternalTaskClient;

public class CheckoutWorker
{
    private final static Logger LOGGER = Logger.getLogger(CheckoutWorker.class.getName());

    public static void main(String[] args)
    {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(10000) // long polling timeout
                .build();
        LOGGER.info("Not yet requests");

        // subscribe to an external task topic as specified in the process
        client.subscribe("payment-checker")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here
                    System.out.println("Test of External Service: Pay");
                    // Get a process variable
                    String game = (String) externalTask.getVariable("game");
                    String type = (String) externalTask.getVariable("type");
                    //boolean shipped = (boolean) externalTask.getVariable("shipped");

                    if(type.equals("DIGITAL")) {
                        LOGGER.info("Game: '" + game + " 'has been ordered with type: '" + type + " and is ready for shipment'");
                    }
                    else
                    {
                        LOGGER.info("Game: '" + game + " 'has been ordered with type: '" + (type != null ? type: "YOu gon get it boi ") + " and is ready for download'");
                    }


                    // Complete the task
                    externalTaskService.complete(externalTask);
                })
                .open();
    }
}
