package com.peng1m.springboot.model;

import javax.persistence.*;

@Entity(name = "starschools")
public class StarSchool {
    @Id
    @Column(name = "starschoolid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int starschoolid;


    public int getSchoolid() {
        return school.getSchoolid();
    }

    public int getStarschoolid() {
        return starschoolid;
    }

    public void setStarschoolid(int starschoolid) {
        this.starschoolid = starschoolid;
    }

    public User getAccount() {
        return account;
    }

    public void setAccount(User account) {
        this.account = account;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @OneToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "account", referencedColumnName = "id")
    private User account;

    @OneToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "schoolid", referencedColumnName = "schoolid")
    private School school;

}
