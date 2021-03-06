# System Integration 2020 Exam Project
- Andeas Jørgensen - https://github.com/DDomino
- Jonatan Bakke https://github.com/JonatanMagnusBakke
- Jonas Hein - https://github.com/Zenzus
- Thomas Ebsen - https://github.com/Srax 
- Exam Project Requirements - [Link](/Misc/ExamProjectRequirements.pdf)
- Video explaining the program - https://www.youtube.com/watch?v=zS_VNNXwQbU

# LINK TO VIDEO
[Video Presentation](https://www.youtube.com/watch?v=zS_VNNXwQbU&feature=youtu.be)

## Table of Contents
- [System Integration 2020 Exam Project](#system-integration-2020-exam-project)
- [LINK TO VIDEO](#link-to-video)
  * [Table of Contents](#table-of-contents)
  * [Business Case - Game Shop](#business-case---game-shop)
  * [Requirements](#requirements)
- [Architecture](#architecture)
  * [Technologies](#technologies)
    + [Monolithic application](#monolithic-application)
    + [Micro services](#micro-services)
    + [Camunda](#camunda)
    + [Kafka](#kafka)
    + [Docker](#docker)
    + [MongoDB & NoSQL](#mongodb---nosql)
    + [Mail Service](#mail-service)
    + [External API (Game Catalog api)](#external-api--game-catalog-api-)
    + [Eureka](#eureka)
    + [Ribbon](#ribbon)
    + [Logging and Errorhandling](#logging-and-errorhandling)
  * [Diagrams](#diagrams)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>


## Business Case - Game Shop

This buisness case is build around the idea of an online game shop.  
A shop has physical stores around the country and a very basic online webshop, made just to browse the companies ware catalog. They want a more morden and dynamic web store. The company want more functionality integrated into their web shop.

## Requirements

1. Ware Catalog on a noSQL database.
2. DataBase with All Customers and emails
3. Employees should be able to validate shipment of physical game sales.
4. Digital GameSales should happen without validation.
6. The system must be as autimatic as possible.

# Architecture

## Technologies
| Technology                        | Usage                                                             |
| ----------------------------------|-------------------------------------------------------------------|
| IntelliJ IDEA                     | Java development environment                                      |
| [MongoDB](#mongodb--nosql)        | NoSQL Database                                                    |
| Compass                           | MongoDB Client                                                    |
| Postman                           | Tool for verifying the API requests                               |
| [Docker](#docker)                 | Used to run our MongoDB and Camunda virtually                     |
| [Camunda](#camunda---jonatan)     | Used to handle our shipment method (BPMN)                         |
| [Kafka](#kafka---jonas)           | Used as our message broker to send messages between microservices |
| [Netflix Eureka](#eureka)         | Used to register and discover our microservices (clients)         |
| [Netflix Eureka](#Netflix Ribbon) | Used for load balancing                                           |
| [Spring Cloud Ribbon](#ribbon)    | Used as a load balancer in our [Api Gateway](/ApiGateway)         |
| [Google SMTP Server](#mail-service---andreas) | Used in our [Mail Service](/mailservice) to send emails when orders are completed |
  


### Monolithic application
<img align="right" src="/Misc/Monolithic.PNG" width=200px >
Our monolithic application is a fictional one, as we did not have a real-life one at hand at start of the project.  

We imagined that the original application, that we were asked to change and update for a more modern setting, were a webshop build in on big service application, with no decoupling, Choreography or orchestration of componenets.  

For our project we decided we would take this huge, monolithic application and decouple it into microservices, that helps with better maintaince and upgrading individual parts of the application, which at the same time greatly helps when it comes to scaling the project in the future. Before going into the Microservice, we will precent an image that can represent what a monolithic application looks like:  

### Micro services 
Microservices is an architecture style where you split your project into small atomic 
pieces. Each piece is a service on its own, and have a small very specific job. 

<img src="/Misc/Microservices.PNG" width=500px>

We use this to help us seperate all our services into small services with as few functions as possible. So that we can make a more
sacaleable product, and easier to update services atomicly. The big advantages of microserivces is how easy it is to load balance together with eureka.
If one of the services has a highload, we can simply open a new service on a new port. And with the help of Eureka we can quickly add it to the overall system.

We use microservice in the way that we have the API gatewway that runs our main entry point, MongoDB service, that handle all functionality for database calls. 
A camunda service that handles our integration of camunda. For a more visually representaion [here](#Diagrams)

### Camunda
Camunda BPM is a lightweight, Java-based framework. It can be used as a standalone process engine server or embedded inside custom Java applications. It offers non-Java developers a REST API and dedicated client libraries to build applications connecting to a remote workflow engine.
Camunda is an open-source decision and workflow automation platform. 
The Camunda Modeler desktop application allows developers to create and edit BPMN process diagrams and DMN decision tables Created files are deployed in the Camunda Engines, which use a BPMN parser to transform BPMN 2.0 XML files, and DMN XML files, into Java Objects, and implements BPMN 2.0 constructs with a set of BPMN Behaviour implementations
Typical use cases for the Camunda BPMN Workflow Engine can be microservices orchestration and human task management.
The Camunda DMN Decision Engine executes business-driven decision tables. It is pre-integrated with the Workflow Engine but can be used as a stand-alone application via REST or inside Java applications

Camunda’s additional web applications provide the following tools for developers and business users:
- Cockpit: A tool for technical process operations enabling users to monitor. workflows and decisions in production, to analyse and solve technical problems.
- Tasklist: Allows end users to work on assigned tasks and provides additional visibility when using the Camunda Workflow Engine for human task management.
- Optimize: An analytics and reporting tool to identify errors and bottlenecks in workflow processes.
- Admin: Allows users to manage Camunda web applications or REST API users. Existing user management can be integrated via LDAP.

We have chosen to use Camunda BPM because it helps us quick and easily create an entire BPM model without having to make it ourselves. The only thing we need to do is to create a business model that has the workflow descried and using the External implementation we can then weave our on features and code into the model. And for further automation we can use their provide Rest API to create processes aka creating an order for a customer. 

We are using Camunda web application for our handling of shipments of the orders that we receive. When an order comes and is confirmed, then the business process begins. The business process in this cause if to figure out if the game withing the order is a physical or digital game. If the game is physical, then an employee needs to take care of handling and creating the shipment to be sent to the customer. If the order is digital, then they will simply get an email with the game. 

Below you can see the BPMN diagram that describes the workflow.  

<img src="Misc/camundabpmn.PNG" width=500px>
The way this works is that when an order enters then the list of games within are iterated through and are created as Shipment Request. The first step is to check if the type of the game is physical or digital, this is done using a decision table. If the is digital, then the order is sent directly to our external service. If is it physical, then it hits a use action. The user action can be confirmed or declined.  

<img src="Misc/dmnmodel.PNG" width=500px>


### Kafka
Apache Kafka is an open-source, distributed streaming platform that allows for the development of real-time event-driven applications. What that boils down to is that kafka allows developers to make applications continuously produce and consume a stream of data.   


The reasons we have chosen to use kafka. Is that kafka is developed in such a way, that it allows for handling large quantities of clients to use the application, at the same time without any loss of data or lagg in the system. Another reason that we have chosen to use kafka is because of how highly accurate Kafka is, in the way it stores the data it is providet, where it maintains the order of the occurrence of the replicated data this also makes Kafka really resilient and fault-tolerant.  


The way we have chosen to use Kafka in our project is that we have made a producer/Shipping-Camunda-Microservice and a consumer/ApiGateway.  

The APIGateway sends an order into camunda, and when it's finished being processed, the Shipping-Microservice then sends a message to Kafka, which contains the order id and the id of the orderlines that order has.  

Then our consumer has been set to lisens to Kafka and uses the data provided to set order as complete and sends a mail to the user that has made the order.  


Here you can see a picture of the data we send into Kafka from our Shipping-Camunda-Microservice It has the orderId aka the overall id for the whole order and it has the orderlineId which is the id for the individual.  

![kafka](/Misc/kafka.png)


This is the response we get from Shipping-Camunda-Microservice when an order has been checked in Camunda, and sent back to Shipping-Camunda-Microservice where it sets them as completed.
```java
2020-12-19 16:38:04.044  INFO 14092 --- [criptionManager] c.s.c.ShippingCamundaController          : [LOGGER] ::: CALLING CLIENT PAYMENT CHECKER
2020-12-19 16:38:04.044  INFO 14092 --- [criptionManager] c.s.c.ShippingCamundaController          : [LOGGER] ::: ! SUCCESS ! ::: DIGITAL ORDER READY FOR DOWNLOAD: 5fde1d1877d888342c61332c
2020-12-19 16:38:04.096  INFO 14092 --- [criptionManager] c.s.c.ShippingCamundaController          : ### Producer sends message: {"orderId":"5fde1d1877d888342c61332d","orderlineId":"5fde1d1877d888342c61332c"}
```

This is the response we get from ApiGateway when it lisens to kafka, and notice an order is set as complete, and then sends a mail to the customer.
```java
2020-12-19 16:38:06.696  INFO 15780 --- [ntainer#0-0-C-1] a.s.mailservice.service.KafkaSetvice     : &&& Message Consumed: [{"orderId":"5fde1d1877d888342c61332d","orderlineId":"5fde1d1877d888342c61332c"}]
```   



### Docker
Docker provides the ability to package and run an application in a loosely isolated environment called a container. The isolation and security allow you to run many containers simultaneously on a given host. Docker also provides an additional layer of abstraction and automation of virtualization on Linux
Docker automates the process of setting up and configuring development environments, building, testing, debugging and deployment. It works with any platform and any programming language, because of this Docker has become the most popular container implementation.
We are using Docker not only because it is the most popular container implementation but because it is very simple to use. The fact that it is popular makes it a good thing to have on our resumes.  
Since we are using Java with Maven all we need to do is to run ‘mvn package’ to build our jar file and add this DockerFile with contents to the root of code project:  

<img src="Misc/dokerfile.PNG">   

If you have docker install in global path write “docker build -t <name>:<version> .” in root and now the Java project Is on Docker.


### MongoDB & NoSQL
MongoDB is a dynamic, object-oriented, and highly scalable NoSQL database.
We chose to use MongoDB to store our data, mainly for its automatic scalability, which allows us to pass our application’s models directly into the database. This made it relatively easy for us to change our data structure on-the-fly without complications.  

It is also easier for us to create relations using the NoSQL language compared to traditional SQL.  
We don’t have to define foreign keys or create conjunction tables, we simple have to add the object which we wish to refer to, into our models and mongo will handle the rest for us.  
This, for example, made adding relations between a `Customer` and an `Order` incredibly straight forward.

All we had to do were to add a `List` of our `Customer-Documents` into the `Order-Document`, save the data to the `Order-Collection`, and voilá, we hvae a `many-to-one` relationship in the database.
This is how our order and customer collections look like in Mongo Compass: 

MongoDB - Order Collection              | MongoDB - Customer Table    
:--------------------------------------:|:-----------------------------------------: 
![mongo](/Misc/mongo-order-table.png)   | ![mongo](/Misc/mongo-customer-table.png)   


As you can see on the `Order-Collection` diagram, a customer is referenced directly by the `Customer-Collection`'s ObjectId which Mongo creates for us when we add a new customer to the database and a target, which in this case is `"customers"`, which is the name of our `customer-collection`.  
We use the same integration model to connect `orderLines` to the `Order-Collection` and to add `Games` to the `OrderLine-Collection`.   

### Mail Service

The service is used to send out automatic mails through gmails smtp service.
We used gmail as it is free, fast and extremely easy to set up. We also had experience
with it from earlier. 
The mail service is running in the APIshop gateway as a consumer serivce. It listen to kafka for orders-broker topics that has been produced from our Shipping-Camunda-Microservice. When an order has been completed in Camunda, the mail service triggers automaticly and dynamicly finds the customers email and sents them a mail. The mail service is attached to the Api-gateway. 

From left to right: Camunda produce a message of an order and sents it to kafka, kafka stores the order data, mailservice cousumes the data and sents out the confirmation emails.
![CKS-diagram](/Misc/CKS-diagram.png)


### External API (Game Catalog api)
Since our project resolves around selling video games, we chose to integrate an external api into our [Mongo-Microservice](/Mongo-Microservice) which provides us with information about various popular video games. 

The external api is hosted by [rawg.io](https://rawg.io/apidocs) and it provides us with plentiful of information aobut all the most popular games, respected platform(s), rating and where to buy the game, however we chose to just generate a ***price range***, ***overall-rating*** and ***game type*** *(digital/physical)* since these were not directly provided.

Our API Controller sorts through [rawg.io](https://rawg.io/apidocs)'s api and store the first twenty games provided, into our own database, so that we can add the games to new orders.
    
### Eureka
Maintaining and connecting multiple microservices that are running on multiple URL's can be troublesome and hard to manage, therefor we chose to use Netflix Eureka.  
Eureka is a tool provided by Netflix that we use to register and discover our microservices (clients) so that they can work together.

We created a [Microservice Discovery App](/Microservice-Discovery-Application) that acts as our discovery client. Whenever one of our microservices goes online, they will be discovered by our app and then registered into Eureka.  
We can then see which microservice has been discovered, either by going to our applications URL at [localhost:8761](http://localhost:8761/) or by looking at the log in 
the console.

This is what the applications console looks like when one of our microservices is discovered, in this example we ran the [Mongo-Microservice](/Mongo-Microservice):  
**Console**
```java
Registered instance MONGO-SERVICE/host.docker.internal:mongo-service:25002 with status UP (replication=true)  
```  

**URL** - [localhost:8761](http://localhost:8761/)
![Eureka](/Misc/eureka.png)

As you can see, `MONGO-SERVICE`, which is the application name of our mongo microservice, is now registered in Eureka.  

We can then connect to the microservice by either sending requests to [host.docker.internal:mongo-service:25002](host.docker.internal:mongo-service:25002) or by using `@FeignClient("mongo-service")` in the code, as we do in our [Api Gateway](/ApiGateway).


### Ribbon
Netflix Ribbon is an Inter Process Communication (IPC) cloud library.
Ribbon load balancer is built into the `@FeignClient`, which provides client-side load balacing when we combine all the microservices rest endpoints in the API Gateway.  
An example of this can be seen in our [MongoClient.java](/ApiGateway/src/main/java/app/repositories/MongoClient.java) insde the API Gateway.  
At the start of the class we provide `@FeignClient("mongo-service")` and `@RibbonClient(name = "mongo-service", configuration = RibbonConfig.class)` which automatically sets up load balancing depending on settings in our [RibbonConfig.class](/ApiGateway/src/main/java/app/config/RibbonConfig.java).


### Logging and Errorhandling
We have implemented error handling into every method that requires it.  
If a method throws an error, the ***error code***, ***a readable error message*** and ***stack trace*** is then printed to the console as a `warning`.

Let's say we use `POSTMAN` to do a `GET REQUEST` for a `java GAME` in our [Mongo-Microservice](/Mongo-Microservice) that will result in an error, because the `game` we are searching for does not exist in the database.

We will then be provided with this message in `POSTMAN`:
```java
"timestamp": "2020-12-19T10:39:50.768+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Game not found",
    "trace": "app.mongo.exceptions.NotFoundException:
```  

And this message in the [Mongo-Microservice](/Mongo-Microservice)'s console:  
```java
ERROR 23808 --- [io-25002-exec-2] GameServiceController : [LOGGER] ::: GAME CONTROLLER ::: id: Game Not found ::: id: fddc6abe3e22f01dc1cfc35sasdsad   
ERROR 23808 --- [io-25002-exec-2] dispatcherServlet : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is app.mongo.exceptions.NotFoundException: Game not found] with root cause   
app.mongo.exceptions.NotFoundException: Game not found
```

## Diagrams

From the diagram we can see how everything is connected, and what port they're running on.
The main service "Shop(gateway)" Runs on the port 25001. This is where we present the data for the customer and future web application. Both it, and the integrated mail service, listen to kafka for new information, to either update order status or send confirmation mails to the customer. 

It connects to all our other micro services through Eureka. Which helps it recognize the services. MongoDB Service handles everything concerning our data. It helps fetch data from the API and push it into the Database. Saves Customers in the data base. And returns data to the API gateway, through Eureak connection. Camunda microservice handles our connection to our camunda docker service, business model and rules. Every result produced form camunda is fed to our Kafka Service.




![system-diagram](/Misc/SystemDiagram.png)







 
