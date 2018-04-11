package com.innprojects.smartbar.Models;

/**
 * Created by simransarin on 09/10/17.
 */

public class User {

    String registration_number;
    String name;
    String phone_number;
    String email;
    String address;
    String cart_value;

    public User(String registration_number, String name, String phone_number, String email, String address, String cart_value) {
        this.registration_number = registration_number;
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.address = address;
        this.cart_value = cart_value;
    }

    public String getCart_value() {
        return cart_value;
    }

    public void setCart_value(String cart_value) {
        this.cart_value = cart_value;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
