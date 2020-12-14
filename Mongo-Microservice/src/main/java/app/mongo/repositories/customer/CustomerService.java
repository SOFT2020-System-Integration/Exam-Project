package app.mongo.repositories.customer;


import app.mongo.models.customer.Customer;
import com.mongodb.MongoException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CustomerService extends MongoRepository<Customer, String>
{
    List<Customer> findAll() throws MongoException;
    Customer findByMail(String mail) throws MongoException;
}