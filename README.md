# System Integration 2020 Exam Project
<ul>
    <li><span>Andreas Jørgensen</span> - <a href="https://github.com/DDomino">Github</a></li>
    <li><span>Jonatan Bakke</span> - <a href="https://github.com/JonatanMagnusBakke">Github</a></li>
    <li><span>Jonas Hein</span> - <a href="https://github.com/Zenzus">Github</a></li>
    <li><span>Thomas Ebsen</span> - <a href="https://github.com/Srax">Github</a></li>
    <li><span><a href="/Misc/ExamProjectRequirements.pdf">Exam Project Requirements</a></span></li>
</ul>
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

### MongoDB
MongoDB is a dynamic, object-oriented, and highly scalable NoSQL database.
We chose to use MongoDB to store our data, mainly for its automatic scalability, which allows us to pass our application’s models directly into the database. This made it relatively easy for us to change our data structure on-the-fly without complications.  

It is also easier for us to create relations using the NoSQL language compared to traditional SQL.  
We don’t have to define foreign keys or create conjunction tables, we simple have to add the object which we wish to refer to, into our models and mongo will handle the rest for us.  
This, for example, made adding relations between a `Customer` and an `Order` incredibly straight forward.

All we had to do were to add a `List` of our `Customer Documents` into the `rder document`, save the data to the `Order Collection`, and voilá, we hvae a `many-to-one` relationship in the database.
This is how our order and customer collections look like in Mongo Compass: 

MongoDB - Order Collection      | MongoDB - Customer Table    
:-------------------------:|:-------------------------: 
![mongo](/Misc/mongo-order-table.png)  |  ![mongo](/Misc/mongo-customer-table.png)   


As you can see on the `Order-Collection` diagram, a customer is referenced directly by the `Customer-Collection`'s ObjectId which Mongo creates for us whenever we add a new customer to the database.
<div style="margin-bottom: 100px"></div>

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
![asd](/Misc/System-Diagram.png)

### Interconnected diagram






 
