package app.mongo.controllers.customer;

import app.mongo.exceptions.NotFoundException;
import app.mongo.models.customer.Customer;
import app.mongo.models.game.Game;
import app.mongo.repositories.customer.CustomerService;
import app.mongo.repositories.game.GameService;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@RepositoryRestController
//@RepositoryRestResource
@ResponseBody
@RestController
@RequestMapping("/customers")
public class CustomerServiceController
{
    @Autowired
    private CustomerService repo;

    @GetMapping("")
    public List<Customer> retrieveAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Customer> retrieveCustomerById(@PathVariable String id) throws NotFoundException, MongoException {
        try {
            return repo.findById(id);
        } catch (MongoException ex) {
            throw new NotFoundException(ex.getCode() + " : " + ex.getMessage());
        }
    }

    @GetMapping("/mail/{title}")
    public Customer retrieveCustomerByMail(@PathVariable String mail) throws NotFoundException, MongoException {
        try {
            return repo.findByMail(mail);
        } catch (MongoException ex) {
            throw new NotFoundException(ex.getCode() + " : " + ex.getMessage());
        }
    }
}
