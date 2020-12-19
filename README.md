# System Integration 2020 Exam Project
- Andeas Jørgensen - https://github.com/DDomino
- Jonatan Bakke https://github.com/JonatanMagnusBakke
- Jonas Hein - https://github.com/Zenzus
- Thomas Ebsen - https://github.com/Srax 
- Exam Project Requirements - [Link](/Misc/ExamProjectRequirements.pdf)


## Table of Contents
- [System Integration 2020 Exam Project](#system-integration-2020-exam-project)
  * [Business Case - Game Shop](#business-case---game-shop)
  * [Requirements:](#requirements-)
- [Architecture](#architecture)
  * [Technologies](#technologies)
    + [Monolithic application (our shop)](#monolithic-application--our-shop-)
    + [Micro services - Andreas](#micro-services---andreas)
    + [Camunda - Jonatan](#camunda---jonatan)
    + [Kafka - Jonas](#kafka---jonas)
    + [Docker](#docker)
    + [MongoDB & NoSQL](#mongodb---nosql)
    + [Mail Service - Andreas](#mail-service---andreas)
    + [Kubenetis?????](#kubenetis-----)
    + [External API (Game Catalog api)](#external-api--game-catalog-api-)
    + [Eureka](#eureka)
    + [Ribbon](#ribbon)
    + [Logging and Errorhandling](#logging-and-errorhandling)
  * [Diagrams](#diagrams)
    + [Interconnected diagram](#interconnected-diagram)

## Business Case - Game Shop

This buisness case is build around the idea of an online game shop. A shop has physical stores around the country and a very basic online webshop, made just to browse the companies ware catalog. They want a more morden and dynamic web store. The company want more functionality integrated into their web shop.

## Requirements

1. Ware Catalog on a noSQL database.
2. DataBase with All Customers and emails
3. Employees should be able to validate shipment of physical game sales.
4. Digital GameSales should happen without validation.
5. Employeees should be able to sent out news/discount mails to their customers
6. The system must be as atomic as possible.

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
| Kubernetes                        | NaN                                                               |
| [Google SMTP Server](#mail-service---andreas) | Used in our [Mail Service](/mailservice) to send emails when orders are completed |
  


### Monolithic application (our shop)
Our monolithic application is a fictional one, as we did not have one at hand at the moment. But we imagined that the original application we was asked to change and update for a more modern setting, was a webshop build in on big service application, with no decoupling, Choreography or orchestration of componenets. For our project we decided we would take this huge, monolithic application and decouple it into microservices, that helps with better maintaince and upgrading individual parts of the application. But alose helps greatly when it comes to scaling. Before going into the Microservice, we will precent an image that can represent what a monolithic application looks like:
<img src="/Misc/Monolithic.PNG" width=200px>

### Micro services - Andreas
    - what
    - why
    - how

### Camunda - Jonatan
    - what
    - why
    - how

### Kafka - Jonas
    - what
    - why
    - how

### Docker
    - what
    - why
    - how

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

### Mail Service - Andreas

The service is used to send out automatic mails through gmails smtp service.
We used gmail as it is free, fast and extremely easy to set up. We also had experience
with it from earlier. 
The mail service is running in the APIshop gateway as a consumer serivce. It listen to kafka for orders-broker topics that has been produced from our Shipping-Camunda-Microservice. When an order has been completed in Camunda, the mail service triggers automaticly and dynamicly finds the customers email and sents them a mail. The mail service is attached to the Api-gateway. 

From left to right: Camunda produce a message of an order and sents it to kafka, kafka stores the order data, mailservice cousumes the data and sents out the confirmation emails.
![CKS-diagram](/Misc/CKS-diagram.png)



### Kubenetis?????
    - what
    - why
    - how

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
It provides client-side load balancing 

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

The over all service we hope to have hosted on Kubenetes and docker at the exam. 


![system-diagram](/Misc/SystemDiagram.png)







 
