# Mini-Project-2  
- Andeas Jørgensen - https://github.com/DDomino
- Jonatan Bakke https://github.com/JonatanMagnusBakke
- Jonas Hein - https://github.com/Zenzus
- Thomas Ebsen - https://github.com/Srax   
- [Assignment Link](MP2-BPMN.pdf)  

## Info
The business process is a very simple "Verify customers age before purchase" process, where the system looks at the items name.
If the name contains "Alcohol", the purchase is halted untill an employee checks the customers ID to see if the customer is old enough to purchase the product.
If they are, the employee inserts the age into the task in camunda and complete the process.

## Setup
What you'll need:
1. Docker to set up a virtual tomcat server with camunda.
2. Camunda Modeler [download here](https://camunda.com/download/modeler/)
3. Your favorite IDE that can run java, we use IntelliJ.

## How to run the project
1. Setup docker's to run Camunda with this command: `docker run -d --name camunda -p 8080:8080 camunda/camunda-bpm-platform:latest`.
2. Log into camunda with this login: `uname: demo`, `pw: demo`.
3. Open the java project and run it (KEEP IT RUNNING).
4. Open up Camunda Modeler and open [AlcoholModel.bpmn](/src/main/resources/AlcoholModel.bpmn) and [Alcoholrules.bpmn](/Alcoholrules.dmn).
5. Deploy both diagrams to this endpoint: `http://localhost:8080/engine-rest/deployment/create`
6. Verify the models in [Camunda](http://localhost:8080/camunda/app/cockpit/default/#/repository?page=1&deploymentsQuery=%5B%5D&deployment=7cf90f66-16b9-11eb-981e-0242ac110002&editMode).
7. Once verified, start new process using the [CheckoutSystem](http://localhost:8080/camunda/app/tasklist/default/#/?filter=b2c46c2c-1384-11eb-a56f-0242ac110002&sorting=%5B%7B%22sortBy%22:%22created%22,%22sortOrder%22:%22desc%22%7D%5D&processStart=Alcohol) (AlcoholModel) 
8. In `item`, write anything containing with alcohol, e.g.: `Beer 10 Alcohol` and in `Amount`write any amount.
9. If done correctly, you should be able to claim the process in the Camunda [Tasklist](http://localhost:8080/camunda/app/tasklist/default/#/?searchQuery=%5B%5D&filter=b2c99c5b-1384-11eb-a56f-0242ac110002&sorting=%5B%7B%22sortBy%22:%22created%22,%22sortOrder%22:%22desc%22%7D%5D). Write in the customers age and complete the process.
10. Once completed, you should be able to see a new message in the java programs console.

