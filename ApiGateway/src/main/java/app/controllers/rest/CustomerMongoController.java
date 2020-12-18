package app.controllers.rest;

import app.exceptions.NotFoundException;
import app.helpers.Encrypt;
import app.models.customer.Customer;
import app.models.game.Game;
import app.repositories.MongoClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/customers")
public class CustomerMongoController {
    private final MongoClient client;
    public CustomerMongoController(MongoClient client) {
        this.client = client;
    }

    @GetMapping("")
    @CrossOrigin(origins = "*") // allow request from any client
    public Collection<Customer> retrieveAllCustomers() throws NotFoundException
    {
        List<Customer> collect = client.customerCollection()
                .stream()
                .collect(Collectors.toList());

        if(!collect.isEmpty()) {
            return collect;
        } else {
            throw new NotFoundException(String.format("Customers not found..."));
        }
    }

    @GetMapping("/id/{id}")
    @CrossOrigin(origins = "*") // allow request from any client
    public Customer retrieveCustomerById(@PathVariable String id) throws NotFoundException
    {
        List<Customer> collect = client.customerCollection()
                .stream()
                .filter(Customer -> Customer.getId().equals(id))
                .collect(Collectors.toList());
        if(!collect.isEmpty()) {
            return collect.get(0);
        } else {
            throw new NotFoundException(String.format("Customer with id '%s' not found...", id));
        }
    }

    @GetMapping("/mail/{mail}")
    @CrossOrigin(origins = "*") // allow request from any client
    public Customer retrieveCustomerByMail(@PathVariable String mail) throws NotFoundException
    {
        List<Customer> collect = client.customerCollection()
                .stream()
                .filter(Customer -> Customer.getMail().equals(mail))
                .collect(Collectors.toList());
        if(!collect.isEmpty()) {
            return collect.get(0);
        } else {
            throw new NotFoundException(String.format("Customer with mail '%s' not found...", mail));
        }
    }

    @PostMapping("/login/{mail}/{password}")
    @CrossOrigin(origins = "*") // allow request from any client
    public Customer loginCustomer(@PathVariable String mail, @PathVariable String password) throws NotFoundException
    {
        Customer customer;
        try {
            customer = retrieveCustomerByMail(mail);
            if(Encrypt.checkPassword(password, customer.getPassword()) == true) {
                return customer;
            } else {
                throw new NotFoundException("Customer with mail " + "'" + mail + "' & password " + "'" + password + "' not found...");
            }
        } catch (Exception ex) {
            throw new RuntimeException("Something went wrong...");
        }
    }
}
