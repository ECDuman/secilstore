package com.secilstore.task.dto;

import com.secilstore.task.model.UserRole;

public class RegisterDto {

    private UserRole userRole;
    private String name;
    private String surname;
    private String phone;

    public RegisterDto(String phone, String name, String surname, UserRole userRole) {
        this.phone = phone;
        this.name = name;
        this.surname = surname;
        this.userRole = userRole;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
