package com.peng1m.springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "starcourses")
public class StarCourse {
    public int getStarcourseid() {
        return starcourseid;
    }

    public void setStarcourseid(int starcourseid) {
        this.starcourseid = starcourseid;
    }

    public User getAccount() {
        return account;
    }

    public void setAccount(User account) {
        this.account = account;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getCourseid() {
        return this.course.getCourseid();
    }

    @Id
    @Column(name = "starcourseid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int starcourseid;

    @OneToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "account", referencedColumnName = "id")
    private User account;

    @OneToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "courseid", referencedColumnName = "courseid")
    private Course course;

}
