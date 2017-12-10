package com.peng1m.springboot.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    @NotEmpty(message = "*Please provide your name")
    private String name;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "*Please provide your password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "*Please provide a valid email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "schoolid")
    private School school;


    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public User(String name, String email, String password, School school) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.school = school;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        if (school != null) {
            return "User [id=" + id + ", name=" + name + ", email=" + email + ",school=" + school.getSchool_name() + "]";
        } else return "User [id=" + id + ", name=" + name + ", email=" + email + "]";
    }
}
