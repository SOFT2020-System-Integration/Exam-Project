package app.shipping;


import app.shipping.controllers.ShippingCamundaController;
import okhttp3.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.camunda.bpm.client.ExternalTaskClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
                        LOGGER.log(Level.INFO, "[LOGGER] ::: ! SUCCESS ! ::: PHYSICAL ORDER SENT: " + game);
                    }
                    if (type.equals("DIGITAL")) {
                        LOGGER.log(Level.INFO, "[LOGGER] ::: ! SUCCESS ! ::: DIGITAL ORDER READY FOR DOWNLOAD: " + game);
                    }
                    // Complete the task
                    externalTaskService.complete(externalTask);
                })
                .open();
        LOGGER.log(Level.INFO, "[LOGGER] ::: LISTENING FOR MESSAGES");
        System.out.println();

        deleteAllDeployments();

        String pathToRules = System.getProperty("user.dir") + "\\src\\main\\resources\\ShippingRules.dmn";
        String pathToModel = System.getProperty("user.dir") + "\\src\\main\\resources\\ShippingModel.bpmn";

        delpoyFileToCamunda("ShippingRules", pathToRules);
        delpoyFileToCamunda("ShippingModel", pathToModel);


    }

    private void delpoyFileToCamunda(String name, String pathToFile) throws IOException {
        OkHttpClient client2 = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("filename", pathToFile,
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File(pathToFile)))
                .addFormDataPart("deployment-name", name)
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:8080/engine-rest/deployment/create")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client2.newCall(request).execute();
    }


    public static void deleteAllDeployments() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:8080/engine-rest/deployment/")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String bodyString = response.body().string();
        Object obj = JSONValue.parse(bodyString);
        JSONArray array = (JSONArray)obj;
        for(Object j : array)
        {
            JSONObject jo = (JSONObject)j;
            String delpoymentId = jo.get("id").toString();
            OkHttpClient client2 = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request2 = new Request.Builder()
                    .url("http://localhost:8080/engine-rest/deployment/" + delpoymentId + "?cascade=true")
                    .method("DELETE", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response2 = client.newCall(request2).execute();
        }

    }

}


