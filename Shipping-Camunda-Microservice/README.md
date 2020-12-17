# Shipping-Camunda-Microservice  
- Andeas Jørgensen - https://github.com/DDomino
- Jonatan Bakke https://github.com/JonatanMagnusBakke
- Jonas Hein - https://github.com/Zenzus
- Thomas Ebsen - https://github.com/Srax  

## Info
Open http://localhost:8080/camunda-welcome/index.html to gain access to the cockpit

## BPMN 
Visualises business processes
Provides common language for all stakeholders in a company
non-technical; business analysts and managers can follow what is happening
technical; easier for software engineer to define operation and also makes it easier to maintain

BPMN is readable and understandable by humans and is self-explanatory when done correctly.
BPMN can be described in standard XML formant for machine use .bpmn

## Setup
What you'll need:
1. Docker to set up a virtual tomcat server with camunda.
2. Camunda Modeler [download here](https://camunda.com/download/modeler/)
3. Your favorite IDE that can run java, we use IntelliJ.

## How to run the project
1. Setup docker's to run Camunda with this command: `docker run -d --name camunda -p 8080:8080 camunda/camunda-bpm-platform:latest`.
2. Log into camunda with this login: `uname: demo`, `pw: demo`.
3. Open the java project and run it (KEEP IT RUNNING).
4. Open up Camunda Modeler and open [GameModel.bpmn](/ShippingModel.bpmn) and [Gamerules.bpmn](/Ganmerules.dmn).
5. Deploy both diagrams to this endpoint: `http://localhost:8080/engine-rest/deployment/create`
6. Verify the models in [Camunda](http://localhost:8080/camunda/app/cockpit/default/#/repository?page=1&deploymentsQuery=%5B%5D&deployment=7cf90f66-16b9-11eb-981e-0242ac110002&editMode).
7. Once verified, start new process using the [GameCheckoutSystem](http://localhost:8080/camunda/app/tasklist/default/#/?filter=b2c46c2c-1384-11eb-a56f-0242ac110002&sorting=%5B%7B%22sortBy%22:%22created%22,%22sortOrder%22:%22desc%22%7D%5D&processStart=Alcohol) (GameModel) 
8. `game` is used for the gameId and `type` is used for defining if the ordered game is a physical copy or a digital one. If `type` is set to PHYSICAL then a employee will need to prepare the order for shipment before checking the `shipping` box on the form. 
10. When the game has been shipped or is a digital download then the microservice is called.

## How to REST API Start process
1. Call endpoint http://localhost:8080/engine-rest/process-definition/key/GameShipment/start with Header: Content-type: application/json and body:
{
    "variables":{
   "game" : {
     "value" : "PostMand Game",
     "type": "String"},
   "type" : {
     "value" : "DIGITAL",
     "type": "String"
    }
  }
}

2. how to post a game http://localhost:25003/shipping/create-process {
        "id": "5fd5efe21469952de5af1bd6",
        "title": "Grand Theft Auto V",
        "currentPrice": 37.2,
        "retailPrice": 47.26,
        "savingsPercentage": 21.29,
        "rating": 3.0,
        "inStock": 783,
        "type": {
            "id": "2",
            "type": "DIGITAL",
            "description": "Physical copy of the game"
        }
    }
    
    ## Setup
1. Kafka Tool 2.0.8 [Download here](https://www.kafkatool.com/download.html)
2. Apache Kafka - unzip [Kafka.zip](kafka.zip)
3. In the Kafka folder, navitage to `config` and edit `server.properties` & `zookeeper.properties` to log to your file path (current path is `C:/thoma/documents/kafka/data/kafka or zookeeper` depending on the file open. This path might not work for you.
4. Your favorite IDE that can run java, we use IntelliJ.
5. A tool to send post requests, we use Postman.
 
## How to run the project

####  On Windows
1. Open two command prompts and navigate to `kafka/bin/windows` in both cmd's: (note, if the file path is too long it may not be able to run)
2. Start Kafka Zookeeper (in cmd 1) `zookeeper-server-start.bat ../../config/zookeeper.properties`  
3. Start Kafka Server (in cmd 2) `kafka-server-start.bat ../../config/server.properties`  
4. Start Kafka Tool 2.0.8 and connect to the server:  
![kafka](/img/kafka-setup.png)    
5. Open and run both programs: `Kafka-Producer` and `Kafka-Consumer`  
6. When both programs are running, send a `POST` request to `localhost:9000/kafka/mail/all`
7. You should now be able to see the message sent in both the `Producer` and the `Consumer`'s consoles.
8. The messages sent can also be seen in Kafka 2.0.8. Open the program and navigate to Kafka -> Topics -> `messsage-topic`. Select on the `data` tab and then click on the green circle to see all the messages.
9. If the messages is shown as binary, head to the `properties` tab and change `Content-Types` Key  and Message to `String`.  
![afka](/img/kafkaprogram.png)

#### On Mac
1. Open two command prompts and navigate to `kafka/bin/` in both cmd's:  
2. Start Kafka Zookeeper (in cmd 1) `zookeeper-server-start.sh ../config/zookeeper.properties`
3. Start Kafka Server (in cmd 2) `kafka-server-start.sh ../config/server.properties`
4. Follow step 4-9 in **On Windows**


