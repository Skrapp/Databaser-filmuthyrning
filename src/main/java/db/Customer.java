package db;

import javax.persistence.*;
import java.sql.Date;
import java.time.Instant;

@Entity
@Table
public class Customer {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;

    @Column(name = "store_id")
    private int store_id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "email")
    private String email;

    @Column(name = "address_id")
    private int address_id;

    @Column(name = "active")
    private int active;

    @Column(name = "create_date")
    private Date create_date;

    @Column(name = "last_update")
    private Instant last_update;

    @Column(name = "address")
    private int address;

    public Customer() {
    }

    public Customer(int customer_id, int store_id, String first_name, String last_name, String email, int address_id, int active, Date create_date, Instant last_update, int address) {
        this.customer_id = customer_id;
        this.store_id = store_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.address_id = address_id;
        this.active = active;
        this.create_date = create_date;
        this.last_update = last_update;
        this.address = address;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Instant getLast_update() {
        return last_update;
    }

    public void setLast_update(Instant last_update) {
        this.last_update = last_update;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
}