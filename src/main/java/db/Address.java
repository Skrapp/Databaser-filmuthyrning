package db;

import org.geolatte.geom.Geometry;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import javax.persistence.*;
import java.awt.*;
import java.time.Instant;



@Entity
@Table
public class Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int address_id;

    @Column(name = "address")
    private String address;

    @Column(name = "address2")
    private String address2;

    @Column(name = "district")
    private String district;

    @Column(name = "city_id")
    private int city_id;

    @Column(name = "postal_code")
    private String postal_code;

    @Column(name = "phone")
    private String phone;


    @Column(name = "location")
    private org.locationtech.jts.geom.Point location;


    @Column(name = "last_update")
    private Instant last_update;

    public Address() {
    }



    public Address(int address_id, String address, String address2, String district, int city_id, String postal_code, String phone, Point location, Instant last_update) {
        GeometryFactory geometryFactory = new GeometryFactory();
        this.address_id = address_id;
        this.address = address;
        this.address2 = address2;
        this.district = district;
        this.city_id = city_id;
        this.postal_code = postal_code;
        this.phone = phone;
        this.location = geometryFactory.createPoint(new Coordinate(0,0));
        this.last_update = last_update;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public org.locationtech.jts.geom.Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        GeometryFactory geometryFactory = new GeometryFactory();
        this.location = geometryFactory.createPoint(new Coordinate(0,0));

    }

    public Instant getLast_update() {
        return last_update;
    }

    public void setLast_update(Instant last_update) {
        this.last_update = last_update;
    }
}