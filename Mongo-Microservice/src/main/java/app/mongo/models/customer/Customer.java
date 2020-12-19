package app.mongo.models.customer;

import app.mongo.helpers.Encrypt;
import app.mongo.models.game.GameType;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Data
@Document(collection = "customers")
public class Customer implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String firstName;
    private String lastName;
    private String mail;
    private String password;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String mail, String password) {
        String x = firstName.toLowerCase();
        String y = lastName.toLowerCase();
        this.firstName = StringUtils.capitalize(x);
        this.lastName = StringUtils.capitalize(y);
        this.mail = mail.toLowerCase();
        this.password = Encrypt.hashPassword(password);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        String x = firstName.toLowerCase();
        return StringUtils.capitalize(x);
    }

    public void setFirstName(String firstName) {
        this.firstName = StringUtils.capitalize(firstName);
    }

    public String getLastName() {
        String x = lastName.toLowerCase();
        return StringUtils.capitalize(x);
    }

    public void setLastName(String lastName) {
        this.lastName = StringUtils.capitalize(lastName);
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Encrypt.hashPassword(password);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}