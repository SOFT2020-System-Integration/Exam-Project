# System Integration 2020 Exam Project.


## Business Case - Game Shop
This buisness case is build around the idea of an online game shop. A shop has physical stores around the country and a very basic online webshop, made just to browse the companies ware catalog. They want a more morden and dynamic web store. The company want more functionality integrated into their web shop.

## Requirements:

1. Ware Catalog on a noSQL database.
2. DataBase with All Customers and emails
3. Employees should be able to validate shipment of physical game sales.
4. Digital GameSales should happen without validation.
5. Employeees should be able to sent out news/discount mails to their customers
6. The system must be as atomic as possible.



## Architecture
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

### MongoDB
<hr/>

MongoDB is a dynamic, object oriented, and highly scalable NoSQL database.
We chose to use MongoDB as our datastore, mainly for its automatic scalability, which allows us to pass our application’s models directly into the database, which made it relatively easy for us to change how our data structure on the fly.  
  
It is also easier for us to create relations using the NoSQL language compared to traditional SQL.  
We don’t have to define foreign keys or create conjunction tables, we simple have to add the object which we wish to refer to into our models and mongo will handle the rest for us, which for example, made adding relations between a customer and an order incredibly straight forward.
 
As an example, this is how our order and customer table looks in mongodb:    
MongoDB - Order Table      | MongoDB - Customer Table    
:-------------------------:|:-------------------------: 
![mongo](/Misc/mongo-order-table.png)  |  ![mongo](/Misc/mongo-customer-table.png)  
As you can see on the Order-Table diagram, a customer is referenced directly by the table's ObjectId 

### Mail Service - Andreas
The service is used to send out automatic mails through gmails smtp service.
We used gmail as it is free, fast and extremely easy to set up. We also had experience
with it from earlier. 
The mail service is running in the APIshop gateway as a consumer serivce. It listen to kafka for orders-broker topics that has been produced from our Shipping-Camunda-Microservice. When an order has been completed in Camunda, the mail service triggers automaticly and dynamicly finds the customers email and sents them a mail.

From left to right: Camunda produce a message of an order and sents it to kafka, kafka stores the order data, mailservice cousumes the data and sents out the confirmation emails.
![CKS-diagram](CKS-diagram.png)



### Kubenetis?????
    - what
    - why
    - how

### External API (Game api) 
    - what
    - why
    - how

### Eureka
    - what
    - why
    - how

### Logging and monitoring
    - why
    - how    

## Diagrams


### Interconnected diagram






 
