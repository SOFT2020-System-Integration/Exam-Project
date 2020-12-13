# Shipping-Camunda-Microservice  
- Andeas Jørgensen - https://github.com/DDomino
- Jonatan Bakke https://github.com/JonatanMagnusBakke
- Jonas Hein - https://github.com/Zenzus
- Thomas Ebsen - https://github.com/Srax  

## Info
Open http://localhost:8080/camunda-welcome/index.html to gain access to the cockpit

## Setup
What you'll need:
1. Docker to set up a virtual tomcat server with camunda.
2. Camunda Modeler [download here](https://camunda.com/download/modeler/)
3. Your favorite IDE that can run java, we use IntelliJ.

## How to run the project
1. Setup docker's to run Camunda with this command: `docker run -d --name camunda -p 8080:8080 camunda/camunda-bpm-platform:latest`.
2. Log into camunda with this login: `uname: demo`, `pw: demo`.
3. Open the java project and run it (KEEP IT RUNNING).
4. Open up Camunda Modeler and open [GameModel.bpmn](/src/main/resources/GameModel.bpmn) and [Gamerules.bpmn](/Ganmerules.dmn).
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

