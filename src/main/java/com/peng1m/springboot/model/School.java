package com.peng1m.springboot.model;

import javax.persistence.*;

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

    @Column(name = "tree_path")
    private String tree_path;

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

    public String getTree_path() {
        return tree_path;
    }

    public void setTree_path(String tree_path) {
        this.tree_path = tree_path;
    }

    public School(String school_name, String description, User manager, String website, String tree_path) {
        this.school_name = school_name;
        this.description = description;
        this.manager = manager;
        this.website = website;
        this.tree_path = tree_path;
    }

    public School() {
    }

    @Override
    public String toString() {
        return "User [id=" + this.schoolid + ", name=" + this.getSchool_name() + ", description=" + this.getDescription() + ",description=" + this.getDescription() + ",website=" + this.getWebsite() + "]";
    }
}
