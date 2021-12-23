package db;


import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
public class City {

    @Id
    @Column(name = "city_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int city_id;

    @Column(name = "city")
    private String city;

    @Column(name = "country_id")
    private int country_id;

    @Column(name = "last_update")
    private Instant last_update;

    public City() {
    }

    public City(int city_id, String city, int country_id, Instant last_update) {
        this.city_id = city_id;
        this.city = city;
        this.country_id = country_id;
        this.last_update = last_update;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public Instant getLast_update() {
        return last_update;
    }

    public void setLast_update(Instant last_update) {
        this.last_update = last_update;
    }
}