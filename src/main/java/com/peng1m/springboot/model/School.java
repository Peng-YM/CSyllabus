package com.peng1m.springboot.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "schools")
public class School {
    @Id
    @Column(name = "schoolid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int schoolid;

    @Column(name = "school_name")
    private String school_name;

    @Column(name = "description")
    private String description;

    //Only allow one manager?
    @OneToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "manager", referencedColumnName = "id")
    private User manager;

    @Column(name = "website")
    private String website;

    @Column(name = "logo_src")
    private String logo_src;

    @OneToMany(mappedBy = "school")
    private List<Course> courses;

    public int getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(int schoolid) {
        this.schoolid = schoolid;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogo_sec() {
        return logo_src;
    }

    public void setLogo_sec(String tree_path) {
        this.logo_src = tree_path;
    }

    public School(String school_name, String description, User manager, String website, String logo_sec) {
        this.school_name = school_name;
        this.description = description;
        this.manager = manager;
        this.website = website;
        this.logo_src = logo_sec;
    }

    public School() {
    }

    @Override
    public String toString() {
        return "User [id=" + this.schoolid + ", name=" + this.getSchool_name() + ", description=" + this.getDescription() + ",description=" + this.getDescription() + ",website=" + this.getWebsite() + "]";
    }
}
