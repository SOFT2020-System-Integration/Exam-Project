package app.models.customer;

import app.helpers.Encrypt;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public class Customer implements Serializable {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String mail;
    private String password;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String mail, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
    }

    public Customer(String id, String firstName, String lastName, String mail, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
 /*
    @Override
    public String toString() {
       return "Customer: " + "id: " + id+"\n" + "Name: " + firstName + ", " + lastName;
    }

  */

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
