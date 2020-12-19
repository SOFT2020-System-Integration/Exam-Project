# System Integration 2020 Exam Project
- Andeas Jørgensen - https://github.com/DDomino
- Jonatan Bakke https://github.com/JonatanMagnusBakke
- Jonas Hein - https://github.com/Zenzus
- Thomas Ebsen - https://github.com/Srax 
- Exam Project Requirements - [Link](/Misc/ExamProjectRequirements.pdf)
<br>

## Business Case - Game Shop
This buisness case is build around the idea of an online game shop. A shop has physical stores around the country and a very basic online webshop, made just to browse the companies ware catalog. They want a more morden and dynamic web store. The company want more functionality integrated into their web shop.

## Requirements:

1. Ware Catalog on a noSQL database.
2. DataBase with All Customers and emails
3. Employees should be able to validate shipment of physical game sales.
4. Digital GameSales should happen without validation.
5. Employeees should be able to sent out news/discount mails to their customers
6. The system must be as atomic as possible.



# Architecture
## Technologies
### Monolithic application (our shop)
    - what    

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

All we had to do were to add a `List` of our `Customer Documents` into the `rder document`, save the data to the `Order Collection`, and voilá, we hvae a `many-to-one` relationship in the database.
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
The mail service is running in the APIshop gateway as a consumer serivce. It listen to kafka for orders-broker topics that has been produced from our Shipping-Camunda-Microservice. When an order has been completed in Camunda, the mail service triggers automaticly and dynamicly finds the customers email and sents them a mail. 

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
    - what
    - why
    - how

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
ERROR 23808 --- [io-25002-exec-2] GameServiceController : [LOGGER] ::: GAME CONTROLLER ::: id: Game Not found ::: fddc6abe3e22f01dc1cfc35sasdsad   
ERROR 23808 --- [io-25002-exec-2] dispatcherServlet : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is app.mongo.exceptions.NotFoundException: Game not found] with root cause   
app.mongo.exceptions.NotFoundException: Game not found
```

## Diagrams
![system-diagram](/Misc/System-Diagram.png)

### Interconnected diagram






 
